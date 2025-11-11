package com.para_ti.chocoapp.data.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.para_ti.chocoapp.data.firebase.Product

fun seedProductos() {
    val db = FirebaseFirestore.getInstance()

    val productos = listOf(
        Product(
            nombre = "Chocolate con sabores.",
            precio = 12.5,
            categoria = "chocolate en bolsa",
            stock = 10,
            temporada = "",
            imagenUrl = "https://res.cloudinary.com/dck3yvr6e/image/upload/v1761958162/chocolate-con-sabores-1-300x300_rxyrau.jpg"
        ),
        Product(
            nombre = "marshmellows.",
            precio = 12.5,
            categoria = "chocolate en bolsa",
            stock = 10,
            temporada = "",
            imagenUrl = "https://res.cloudinary.com/dck3yvr6e/image/upload/v1761958167/WhatsApp-Image-2020-07-06-at-17.05.56-300x300_r74ewi.jpg"
        ),
        Product(
            nombre = "Chocolate con frutas.",
            precio = 12.5,
            categoria = "chocolate en bolsa",
            stock = 10,
            temporada = "",
            imagenUrl = "https://res.cloudinary.com/dck3yvr6e/image/upload/v1761958162/chocolate-con-sabores-1-300x300_rxyrau.jpg"
        ),
        Product(
            nombre = "Chocolate con relleno.",
            precio = 12.5,
            categoria = "chocolate en bolsa",
            stock = 10,
            temporada = "",
            imagenUrl = "https://res.cloudinary.com/dck3yvr6e/image/upload/v1761958155/bolsa-chocolates-rellenos-100-g-1-300x300_llhq0a.jpg"
        ),
        Product(
            nombre = "Chocolate con sabores.",
            precio = 12.5,
            categoria = "chocolate en bolsa",
            stock = 10,
            temporada = "",
            imagenUrl = "https://res.cloudinary.com/dck3yvr6e/image/upload/v1761958162/chocolate-con-sabores-1-300x300_rxyrau.jpg"
        ),
        Product(
            nombre = "Bombones con crema.",
            precio = 12.5,
            categoria = "chocolate en bolsa",
            stock = 10,
            temporada = "",
            imagenUrl = "https://res.cloudinary.com/dck3yvr6e/image/upload/v1761958150/bolsa-bombones-200-gr-300x300_nu9jrw.jpg"
            //
        ),
        Product(
            nombre = "Canasta de Bambu.",
            precio = 22.5,
            categoria = "Chocolate en caja artesanal",
            stock = 10,
            temporada = "",
            imagenUrl = "https://res.cloudinary.com/dck3yvr6e/image/upload/v1761958292/canasta-de-bamb%C3%BA-con-cinturon-350-g-300x300_fiou25.jpg"
        ),
        Product(
            nombre = "Caja mimbre pequena",
            precio = 22.5,
            categoria = "Chocolate en caja artesanal",
            stock = 10,
            temporada = "",
            imagenUrl = "https://res.cloudinary.com/dck3yvr6e/image/upload/v1761958290/caja-mimbre-peque%C3%B1a-chocolates-surtidos-250-g-300x300_zxx2og.jpg"
        ),
        Product(
            nombre = "Caja mimbre Grande",
            precio = 42.5,
            categoria = "Chocolate en caja artesanal",
            stock = 10,
            temporada = "",
            imagenUrl = "https://res.cloudinary.com/dck3yvr6e/image/upload/v1761958287/caja-mimbre-grande-chocolates-surtidos-350-g-300x300_lfgcgk.jpg"
        ),
        Product(
            nombre = "Canasta surtida Grande.",
            precio = 44.5,
            categoria = "Chocolate en caja artesanal",
            stock = 10,
            temporada = "",
            imagenUrl = "https://res.cloudinary.com/dck3yvr6e/image/upload/v1761958285/canasta-surtida-grande-300x300_tlakh1.jpg"
        ),

        Product(
            nombre = "Caja de madera pequena",
            precio = 22.5,
            categoria = "Chocolate en caja artesanal",
            stock = 10,
            temporada = "",
            imagenUrl = "https://res.cloudinary.com/dck3yvr6e/image/upload/v1761958282/caja-de-madera-peque%C3%B1a-1-300x300_i0lnog.jpg"
        ),
//
        Product(
            nombre = "Caja dorada con trufas",
            precio = 32.5,
            categoria = "Chocolate en envase de regalo",
            stock = 10,
            temporada = "",
            imagenUrl = "https://res.cloudinary.com/dck3yvr6e/image/upload/v1761958348/caja-dorada-con-truffas-de-licor-220-g-300x300_l6fj6p.jpg"
        ),
        Product(
            nombre = "Caja dorada con trufas",
            precio = 32.5,
            categoria = "Chocolate en envase de regalo",
            stock = 10,
            temporada = "",
            imagenUrl = "https://res.cloudinary.com/dck3yvr6e/image/upload/v1761958282/caja-de-madera-peque%C3%B1a-1-300x300_i0lnog.jpg"
        ),
        Product(
            nombre = "Caja dorada con trufas de licor",
            precio = 42.5,
            categoria = "Chocolate en envase de regalo",
            stock = 10,
            temporada = "",
            imagenUrl = "https://res.cloudinary.com/dck3yvr6e/image/upload/v1761958346/caja-dorada-con-truffas-de-licor-200g-300x300_nas2z2.jpg"
        ),

        Product(
            nombre = "Caja rectangular rosada",
            precio = 52.5,
            categoria = "Chocolate en envase de regalo",
            stock = 10,
            temporada = "",
            imagenUrl = "https://res.cloudinary.com/dck3yvr6e/image/upload/v1761958343/CAJA-RECTANGULAR-rosada-300x300_agf64n.png"
        ),
        Product(
            nombre = "Caja guinda octagonal",
            precio = 32.5,
            categoria = "Chocolate en envase de regalo",
            stock = 10,
            temporada = "",
            imagenUrl = "https://res.cloudinary.com/dck3yvr6e/image/upload/v1761958339/caja-guinda-octogonal-300x300_qkj18p.jpg"
        ),
        Product(
            nombre = "Caja guinda octagonal",
            precio = 32.5,
            categoria = "Chocolate en envase de regalo",
            stock = 10,
            temporada = "",
            imagenUrl = "https://res.cloudinary.com/dck3yvr6e/image/upload/v1761958339/caja-guinda-octogonal-300x300_qkj18p.jpg"
        ),
        //
        Product(
            nombre = "Grajeas chocolate con mani",
            precio = 12.5,
            categoria = "grageas",
            stock = 10,
            temporada = "",
            imagenUrl = "https://res.cloudinary.com/dck3yvr6e/image/upload/v1761958532/revcaja-gragea-man%C3%AD-200-gr-300x300_cfn55g.jpg"
        ),
        Product(
            nombre = "Grajeas chocolate con mani",
            precio = 12.5,
            categoria = "grageas",
            stock = 10,
            temporada = "",
            imagenUrl = "https://res.cloudinary.com/dck3yvr6e/image/upload/v1761958532/revcaja-gragea-man%C3%AD-200-gr-300x300_cfn55g.jpg"
        ),
        Product(
            nombre = "Grajeas chocolate con almendras",
            precio = 12.5,
            categoria = "grageas",
            stock = 10,
            temporada = "",
            imagenUrl = "https://res.cloudinary.com/dck3yvr6e/image/upload/v1761958529/revcaja-gragea-almendra-200-gr-300x300_ncymae.jpg"
        ),
        Product(
            nombre = "Grajeas chocolate con macarron",
            precio = 12.5,
            categoria = "grageas",
            stock = 10,
            temporada = "",
            imagenUrl = "https://res.cloudinary.com/dck3yvr6e/image/upload/v1761958529/revcaja-gragea-almendra-200-gr-300x300_ncymae.jpg"
        ),


        )

    productos.forEach { producto ->
        db.collection("productos")
            .add(producto)
            .addOnSuccessListener { docRef ->
                println("Producto agregado con ID: ${docRef.id}")
            }
            .addOnFailureListener { e ->
                println("Error al agregar producto: ${e.message}")
            }
    }
}