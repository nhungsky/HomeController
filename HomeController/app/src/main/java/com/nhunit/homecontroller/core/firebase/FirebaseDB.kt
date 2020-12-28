package com.nhunit.homecontroller.core.firebase

import android.content.Context
import android.os.SystemClock
import androidx.appcompat.widget.SwitchCompat
import com.google.firebase.database.*
import com.google.gson.Gson
import java.lang.Exception
import java.util.*
import kotlin.random.Random

object FirebaseDB {
    var timerCheck: Timer? = null
    private val database = FirebaseDatabase.getInstance()
    private var lastActive = 0L

    // Key khóa
    private val DANG_HOAT_DONG = database.getReference("DANG_HOAT_DONG")
    val DEN_PHONG = database.getReference("DEN_PHONG")
    val DEN_VUON = database.getReference("DEN_VUON")
    val DEN_TV = database.getReference("DEN_TV")
    val DEN_QUAT = database.getReference("DEN_QUAT")

    // Kiểm tra định kỳ lúc đang hoạt động, khi không có kết nối thì thông báo cho người dùng
    fun Context.KiemTraTrangThaiThietBiDinhKy(trangThaiHienTai: (Boolean) -> Unit) {
        Timer().schedule(object : TimerTask() {
            override fun run() {
                DANG_HOAT_DONG.setValue(0)
            }
        }, 100,2000)
        DANG_HOAT_DONG.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.getValue(Long::class.java)
                if (value == 1L) {
                    lastActive = System.currentTimeMillis()
                    timerCheck?.cancel()
                    timerCheck = null
                    trangThaiHienTai.invoke(true)
                } else {
                    if (timerCheck == null) {
                        timerCheck = Timer()
                        timerCheck?.schedule(object : TimerTask(){
                            override fun run() {
                                trangThaiHienTai.invoke(false)
                            }
                        },2000)
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                trangThaiHienTai.invoke(false)
            }
        })
    }

    // Lúc khởi động, kiểm tra xem thiết bị có đang hoạt động không. Nếu không thì thông báo cho người dùng
    fun ThietBiCoDangHoatDongHayKhong(onFinish: (Boolean) -> Unit) {
        DANG_HOAT_DONG.setValue(0L).addOnSuccessListener {
            Timer().schedule(object : TimerTask() {
                override fun run() {
                    DANG_HOAT_DONG.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            try {
                                val value = snapshot.getValue(Long::class.java)
                                onFinish.invoke(value == 1L)
                            } catch (e: Exception) {
                                onFinish.invoke(false)
                                e.printStackTrace()
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            onFinish.invoke(false)
                        }
                    })
                }
            }, 1000)

        }.addOnFailureListener {
            it.printStackTrace()
        }
    }

    // Hàm bật đèn phòng
    fun Context.BatDenPhong() {
        DEN_PHONG.setValue(1)
    }

    // Hàm tắt đèn phòng
    fun Context.TatDenPhong() {
        DEN_PHONG.setValue(0)
    }

    fun SwitchCompat.GanSuKienVaTrangThai(dbReference: DatabaseReference) {
        setOnClickListener {
            dbReference.setValue(if (isChecked) 1 else 0)
        }
        dbReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val value = snapshot.getValue(Long::class.java)
                    isChecked = value == 1L
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    //Hàm bật đèn vườn
    fun Context.BatDenVuon() {
        DEN_VUON.setValue(1)
    }

    //Hàm tắt đèn vườn
    fun Context.TatDenVuon() {
        DEN_VUON.setValue(0)
    }

    // Hàm bật đèn ti vi
    fun Context.BatDenTiVi() {
        DEN_TV.setValue(1)
    }

    // Hàm tắt đèn ti vi
    fun Context.TatDenTiVi() {
        DEN_TV.setValue(0)
    }

    // Hàm bật đèn quạt
    fun Context.BatDenQuat() {
        DEN_QUAT.setValue(1)
    }

    // Hàm tắt đèn quạt
    fun Context.TatDenQuat() {
        DEN_QUAT.setValue(0)
    }
}