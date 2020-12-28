package com.nhunit.homecontroller.ui.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nhunit.homecontroller.R
import com.nhunit.homecontroller.core.firebase.FirebaseDB.DEN_PHONG
import com.nhunit.homecontroller.core.firebase.FirebaseDB.DEN_QUAT
import com.nhunit.homecontroller.core.firebase.FirebaseDB.DEN_TV
import com.nhunit.homecontroller.core.firebase.FirebaseDB.DEN_VUON
import com.nhunit.homecontroller.core.firebase.FirebaseDB.GanSuKienVaTrangThai
import com.nhunit.homecontroller.core.firebase.FirebaseDB.KiemTraTrangThaiThietBiDinhKy
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        KiemTraTrangThaiThietBiDinhKy {
            runOnUiThread {
                adIvTrangThai.setImageResource(
                    if (it) R.drawable.logo_on else R.drawable.logo_off
                )
                adScDenPhongBtn.isEnabled = it
                adScDenVuonBtn.isEnabled = it
                adScTVBtn.isEnabled = it
                adScQuatBtn.isEnabled = it
            }
        }

        initCacSuKienCapNhatTrangThaiNut()
    }

    private fun initCacSuKienCapNhatTrangThaiNut() {
        adScDenPhongBtn.GanSuKienVaTrangThai(DEN_PHONG)
        adScDenVuonBtn.GanSuKienVaTrangThai(DEN_VUON)
        adScTVBtn.GanSuKienVaTrangThai(DEN_TV)
        adScQuatBtn.GanSuKienVaTrangThai(DEN_QUAT)
    }


}