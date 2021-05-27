package com.example.projectandroid.model

import java.io.Serializable

class Cart : Serializable {
    var id: Int
    var idUser: Int
    var tensanpham: String
    var soluong: Int
    var ngaymuahang: String
    var giasanpham: Int
    var tenhuonghieu: String? = null
    var imageSanPham: String
    var sosanphamtonkho = 0

    constructor(id: Int, idUser: Int, tensanpham: String, soluong: Int, ngaymuahang: String, giasanpham: Int, imageSanPham: String) {
        this.id = id
        this.idUser = idUser
        this.tensanpham = tensanpham
        this.soluong = soluong
        this.ngaymuahang = ngaymuahang
        this.giasanpham = giasanpham
        this.imageSanPham = imageSanPham
    }

    constructor(id: Int, idUser: Int, tensanpham: String, soluong: Int, ngaymuahang: String, giasanpham: Int, tenhuonghieu: String?, sosanphamtonkho: Int, imageSanPham: String) {
        this.id = id
        this.idUser = idUser
        this.tensanpham = tensanpham
        this.soluong = soluong
        this.ngaymuahang = ngaymuahang
        this.giasanpham = giasanpham
        this.tenhuonghieu = tenhuonghieu
        this.imageSanPham = imageSanPham
        this.sosanphamtonkho = sosanphamtonkho
    }
}
