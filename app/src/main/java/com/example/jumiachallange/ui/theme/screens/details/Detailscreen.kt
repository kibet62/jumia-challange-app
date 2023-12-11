package com.example.jumiachallange.ui.theme.screens.details

import android.annotation.SuppressLint
import android.net.http.UrlRequest
import android.util.Log
import android.widget.RatingBar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.paging.ExperimentalPagingApi
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.example.jumiachallange.data.remote.Status
import com.example.jumiachallange.model.configarations.Configurations
import com.example.jumiachallange.model.product.ProductResponse
import com.example.jumiachallange.ui.theme.screens.state.ErrorItem
import com.example.jumiachallange.ui.theme.screens.state.LoadingView


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalPagingApi::class, ExperimentalCoilApi::class)
@Composable
fun DetailsScreen(
    navController: NavHostController,
    detailsViewModel: ViewModel = hiltViewModel(),
    sku: String,
    configurationsViewModel: ViewModel = hiltViewModel()
) {


    val res by configurationsViewModel.readConfigurationsLiveData.observeAsState()

    LaunchedEffect(true) {
        detailsViewModel.getProduct(sku = sku.toInt())
    }
    detailsViewModel.productLiveData.observeAsState().let {

        when(it.value?.status){

            UrlRequest.Status.SUCCESS -> {

                it.value!!.data?.let { product->
                    if(it.value!!.data?.success == true){

                        res.let {config ->
                            if (config != null) {
                                MainContent(navController, product, res)

                            }
                        }
                    }else{
                        ErrorContent(navController)
                    }


                }
            }
            Status.LOADING -> {
                LoadingView(modifier = Modifier.fillMaxSize().background(Color.White))
            }
            Status.ERROR -> {
                ErrorContent(navController)

            }
            else -> {
                ErrorContent(navController)

            }
        }
    }



}

@OptIn(ExperimentalCoilApi::class, ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun ErrorContent(navController: NavHostController) {
    Scaffold(
        topBar = { DetailsTopBar(onClicked = {}, "Opps! An Error Occurred") },
        content = {
            ErrorItem(
                message = "No Result Found For the Product",
                modifier = Modifier.fillMaxSize(),
                onClickRetry = { navController.popBackStack() }
            )
        }
    )
}
@OptIn(ExperimentalCoilApi::class, ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun MainContent(
    navController: NavHostController,
    product: ProductResponse,
    config: Configurations?
) {
    Scaffold(

        topBar = { DetailsTopBar(onClicked = {navController.popBackStack()}, product.metadata!!.name) },
        content = {

            Column(
                verticalArrangement = Arrangement.Top) {


                LazyRow(Modifier.padding(all = 8.dp)) {
                    items(
                        items = product.metadata!!.imageList
                    ) {
                        ProductImages(
                            image = it
                        )
                    }
                }
                Divider()
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)) {

                    Text(
                        text = buildAnnotatedString {
                            append(config?.metadata?.currency?.currencySymbol.toString())
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                                append(product.metadata?.specialPrice.toString())
                            }

                        },
                        style = TextStyle(fontWeight = FontWeight.Medium),
                        color = Color.Black,
                        fontSize = MaterialTheme.typography.body1.fontSize,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(modifier = Modifier.padding(start = 5.dp),
                        text = buildAnnotatedString {
                            append(config?.metadata?.currency?.currencySymbol.toString())
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                                append(product.metadata?.price.toString())
                            }

                        },
                        style = TextStyle(textDecoration = TextDecoration.LineThrough),
                        color = Color.Gray,
                        fontSize = MaterialTheme.typography.body1.fontSize,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Box(modifier = Modifier
                        .align(Alignment.CenterVertically) ){

                        Text(
                            text = buildAnnotatedString {
                                append(" ")
                                append(product.metadata?.maxSavingPercentage.toString())
                                append("% ")
                            },
                            style = TextStyle(fontWeight = FontWeight.Black),
                            color = Color.Black,
                            fontSize = MaterialTheme.typography.caption.fontSize,
                            maxLines = 1)
                    }

                }
                Divider()
                Row(modifier = Modifier.padding(10.dp)) {

                    var rating = product.metadata!!.rating.average.toFloat()
                    RatingBar(
                        value = rating,
                        config = RatingBarConfig()
                            .size(15.dp)
                            .style(RatingBarStyle.HighLighted),
                        onValueChange = {
                            rating = it
                        },
                        onRatingChanged = {
                            Log.d("TAG", "onRatingChanged: $it")
                        }
                    )
                    Text(
                        text = buildAnnotatedString {
                            append(" ")
                            append(product.metadata.rating.ratingsTotal.toString())
                            append(" ")
                            append("ratings")
                        },
                        style = TextStyle(fontWeight = FontWeight.Medium),
                        color = Color.Blue,
                        fontSize = MaterialTheme.typography.caption.fontSize,
                        maxLines = 1)

                }
                Divider()
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.LightGray.copy(alpha = .3f)
                ) {

                    Text(
                        modifier = Modifier.padding(10.dp),
                        text = "SPECIFICATIONS",
                        style = TextStyle(fontWeight = FontWeight.SemiBold),
                        color = Color.Black,
                        fontSize = MaterialTheme.typography.h6.fontSize,
                    )

                }
                Surface(modifier = Modifier.background(Color.White).padding(10.dp)) {
                    Column() {
                        Text(
                            modifier = Modifier.padding(top = 10.dp),
                            text = product.metadata!!.name,
                            style = TextStyle(fontWeight = FontWeight.Normal),
                            color = Color.Black,
                            fontSize = MaterialTheme.typography.h6.fontSize,
                            maxLines = 1)

                        Text(

                            text = product.metadata.brand,
                            style = TextStyle(fontWeight = FontWeight.Normal),
                            color = Color.Black,
                            fontSize = MaterialTheme.typography.body1.fontSize,
                            maxLines = 1)
                        Text(
                            modifier = Modifier.padding(top = 10.dp),
                            text = product.metadata.sellerEntity.deliveryTime,
                            style = TextStyle(fontWeight = FontWeight.Normal),
                            color = Color.Blue.copy(.8f),
                            fontSize = MaterialTheme.typography.body1.fontSize,
                            maxLines = 1)


                        Text(
                            modifier = Modifier.padding(top = 10.dp),
                            text = "DESCRIPTION",
                            style = TextStyle(fontWeight = FontWeight.SemiBold),
                            color = Color.Black,
                            fontSize = MaterialTheme.typography.h6.fontSize,
                        )
                        Text(
                            modifier = Modifier.padding(top = 5.dp),
                            text = product.metadata.summary.shortDescription,
                            style = TextStyle(fontWeight = FontWeight.Normal),
                            color = Color.Gray,
                            fontSize = MaterialTheme.typography.body1.fontSize,
                        )


                    }
                }
            }
        })
}

@ExperimentalCoilApi
@Composable
fun ProductImages(image: String) {
    Surface(modifier = Modifier.background(Color.LightGray.copy(alpha = .2f))) {

        Card(
            modifier = Modifier
                .size(250.dp)
                .padding(all = 3.dp),
            elevation = 4.dp,
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier.padding(all = 10.dp),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(image)
                    .crossfade(1000)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Fit,

                ) {
                val state = painter.state
                if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                    CircularProgressIndicator()
                } else {
                    SubcomposeAsyncImageContent()
                }
            }
        }

    }

}