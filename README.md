# Ecommerce
EduApp is an e-commerce app that fetches data from a public API and displays it in a
RecyclerView with different view types for products, using the Material Design system for
Android. Use Clean Architecture, Dagger-Hilt for dependency injection, and Room for local
caching. 

## Screens
- Home 
- Categories
- Cart
- Product details

## Features
- Search products
- Cart logic
- Cache products for offline better experience

## Tech
- [Navigation component](https://developer.android.com/guide/navigation?gclid=Cj0KCQjwmouZBhDSARIsALYcouoEzVh473bVV4ZKen9DrESxlnro7zH0Ue17y8ItumGVs558UFtPZLMaArvcEALw_wcB&gclsrc=aw.ds) - App navigation -> Single activity concept.
- [Safe Args](https://developer.android.com/guide/navigation/navigation-pass-data) - For type-safe passing objects between screens.
- [Dagger-Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - For dependency injection.
- [Picasso](https://github.com/square/picasso) - Image loading.
- [Room](https://www.google.com/aclk?sa=l&ai=DChcSEwi9grKR_Zf6AhXL29UKHXDAAQEYABAAGgJ3cw&sig=AOD64_0mj-zhfeWtng8VaNttu5jtKW8Rrg&q&adurl&ved=2ahUKEwiCmauR_Zf6AhURGewKHVeZCdgQ0Qx6BAgEEAE) - Caching and saving wishlist & cart items.
- [Retrofit](https://square.github.io/retrofit/) - Network.
- [Gson](https://github.com/square/retrofit/tree/master/retrofit-converters/gson) - Serialization.
- [Kotlin coroutines](https://developer.android.com/kotlin/coroutines) - Concurrency.
- [Datastore](https://developer.android.com/topic/libraries/architecture/datastore?gclid=Cj0KCQjwmouZBhDSARIsALYcouqqVwqHCWPrxpL_ai4jk13C_i1-T3bVXUeBsp2L8bhfWAB5bKcHedcaApyDEALw_wcB&gclsrc=aw.ds) - Used for presisting filter values.
- [Livedata](https://developer.android.com/topic/libraries/architecture/livedata)
- [Viewmodel](https://developer.android.com/topic/libraries/architecture/viewmodel?gclid=Cj0KCQjwmouZBhDSARIsALYcouowU4bI_Dxt9XRlsd4-qAblwn2Vk3jgw86XJFJdhJuraSbTxzGNgwMaAvejEALw_wcB&gclsrc=aw.ds)
- [View Binding](https://developer.android.com/topic/libraries/view-binding)

## Screenshots
<kbd><img width="216" height="432" src="Pics/Home.png"></kbd>
<kbd><img width="216" height="432" src="Pics/Search.png"></kbd>
<kbd><img width="216" height="432" src="Pics/Categories.png"></kbd>
<kbd><img width="216" height="432" src="Pics/Cart.png"></kbd>
<kbd><img width="216" height="432" src="Pics/pDetails1.png"></kbd>
<kbd><img width="216" height="432" src="Pics/pDetails2.png"></kbd>
