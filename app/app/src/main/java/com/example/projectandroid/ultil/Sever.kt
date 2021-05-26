package com.example.projectandroid.ultil

class Server {
    var localhost = "192.168.1.6:8080"
    var path = "http://" + localhost + "/server/getloaisp.php"
    var pathNew = "http://" + localhost + "/server/getsanphammoinhat.php"
    var pathPhone = "http://" + localhost + "/server/getsanpham.php"
    var pathComment = "http://" + localhost + "/server/getcomment.php"
    var pathUser = "http://" + localhost + "/server/getUser.php"
    var pathInsertSanPham = "http://" + localhost + "/server/insertSanPham.php"
    var pathDonHang = "http://" + localhost + "/server/getDonHang.php"
    var pathCapNhatDonHang = "http://" + localhost + "/server/updateDonHang.php"
    var pathXoaDonHang = "http://" + localhost + "/server/deleteDonHang.php"
    var commentUser = "http://" + localhost + "/server/insertComment.php"
    var insertUser = "http://" + localhost + "/server/insertUser.php"
    var getDiscount = "http://" + localhost + "/server/getDiscount.php"
    var updateUser = "http://" + localhost + "/server/updateUser.php"
    var confirmCart = "http://" + localhost + "/server/confirmCart.php"
    var insertDonHangChiTiet = "http://" + localhost + "/server/insertDonHangChiTiet.php"
    var pathDonHangChiTiet = "http://" + localhost + "/server/getDonHangChiTiet.php"
}