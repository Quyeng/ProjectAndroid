package com.example.projectandroid.model
/*
Team 10
 */
import java.io.Serializable

class Product : Serializable {
    var id = 0
    var nameProduct: String? = null
    var priceProduct: Int? = null
    var imageProduct: String? = null
    var descriptionProduct: String? = null
    var idProduct = 0
    var id_thuonghieu = 0
    var sosanphamdaban = 0
    var sosanphamcontonkho = 0
    var diemdanhgia: String? = null

    constructor(
        id: Int,
        nameProduct: String?,
        priceProduct: Int?,
        imageProduct: String?,
        descriptionProduct: String?,
        idProduct: Int
    ) {
        this.id = id
        this.nameProduct = nameProduct
        this.priceProduct = priceProduct
        this.imageProduct = imageProduct
        this.descriptionProduct = descriptionProduct
        this.idProduct = idProduct
    }

    constructor() {}
    constructor(
        id: Int,
        nameProduct: String?,
        priceProduct: Int?,
        imageProduct: String?,
        descriptionProduct: String?,
        idProduct: Int,
        id_thuonghieu: Int,
        sosanphamdaban: Int,
        sosanphamcontonkho: Int,
        diemdanhgia: String?
    ) {
        this.id = id
        this.nameProduct = nameProduct
        this.priceProduct = priceProduct
        this.imageProduct = imageProduct
        this.descriptionProduct = descriptionProduct
        this.idProduct = idProduct
        this.id_thuonghieu = id_thuonghieu
        this.sosanphamdaban = sosanphamdaban
        this.sosanphamcontonkho = sosanphamcontonkho
        this.diemdanhgia = diemdanhgia
    }
}
