package com.example.baseproyect

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.GradientDrawable.OVAL
import android.os.Build
import android.widget.LinearLayout
import androidx.annotation.ColorRes
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng


object ViewUtils {
    fun bitmapDescriptorFromVector(
        context: Context,
        vectorResId: Int
    ): BitmapDescriptor {
        val vectorDrawable =
            ContextCompat.getDrawable(context, vectorResId)
        vectorDrawable?.setBounds(
            0,
            0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )
        val bitmap = vectorDrawable?.intrinsicHeight?.let {
            Bitmap.createBitmap(
                vectorDrawable.intrinsicWidth,
                it,
                Bitmap.Config.ARGB_8888
            )
        }
        val canvas = bitmap?.let { Canvas(it) }
        canvas?.let { vectorDrawable.draw(it) }
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    fun getBusIcon(line: String): Int =
        when (line) {
            "500" -> {
                R.drawable.ic_autobus_yellow
            }
            "501" -> {
                R.drawable.ic_autobus
            }
            "502" -> {
                R.drawable.ic_autobus_502
            }
            "503" -> {
                R.drawable.ic_autobus_blue
            }
            "504" -> {
                R.drawable.ic_autobus_green
            }
            "505" -> {
                R.drawable.ic_autobus_marron
            }
            else -> {
                R.drawable.ic_autobus_502
            }
        }

    fun setBackgroundColorShape(context: Context, @ColorRes color: Int,wid: Int): Drawable {
        val gd = GradientDrawable()
        gd.setColor(ContextCompat.getColor(context, color))
        gd.shape = OVAL
        gd.setStroke(wid, Color.BLACK)
        return gd
    }

    public val RECORRIDO_AZUL = listOf<LatLng>(
        LatLng(-37.330472, -59.112383),
        LatLng(-37.331054, -59.113960),
        LatLng(-37.331626, -59.115398),
        LatLng(-37.332172, -59.1169),
        LatLng(-37.333339, -59.116171),
        LatLng(-37.333672, -59.116982),
        LatLng(-37.334126, -59.118186),
        LatLng(-37.333979, -59.118890),
        LatLng(-37.333776, -59.11992),
        LatLng(-37.333637, -59.120744),
        LatLng(-37.333905, -59.121425),
        LatLng(-37.334306, -59.122517),
        LatLng(-37.334732, -59.123605),
        LatLng(-37.335002, -59.124305),
        LatLng(-37.335552, -59.125878),
        LatLng(-37.335834, -59.126614),
        LatLng(-37.336117, -59.127351),
        LatLng(-37.336676, -59.128769),
        LatLng(-37.335534, -59.129494),
        LatLng(-37.334341, -59.130227),
        LatLng(-37.333097, -59.130930),
        LatLng(-37.331910, -59.131611),
        LatLng(-37.330733, -59.132330),
        LatLng(-37.329530, -59.133049),
        LatLng(-37.328363, -59.133712),
        LatLng(-37.328907, -59.135205),
        LatLng(-37.329389, -59.136522),
        LatLng(-37.329841, -59.137678),
        LatLng(-37.330383, -59.139166),
        LatLng(-37.32906, 59.139946),
        LatLng(-37.328506, -59.138515),
        LatLng(-37.328049, -59.137345),
        LatLng(-37.326868, -59.138064),
        LatLng(-37.325674, -59.138746),
        LatLng(-37.325179, -59.137399),
        LatLng(-37.324624, -59.135961),
        LatLng(-37.324069, -59.134475),
        LatLng(-37.323497, -59.133032),
        LatLng(-37.323196, -59.132222),
        LatLng(-37.322943, -59.131423),
        LatLng(-37.321718, -59.132136),
        LatLng(-37.321089, -59.132479),
        LatLng(-37.320460, -59.132823),
        LatLng(-37.319231, -59.133510),
        LatLng(-37.318053, -59.134243),
        LatLng(-37.317482, -59.13457),
        LatLng(-37.316912, -59.134911),
        LatLng(-37.315707, -59.135614),
        LatLng(-37.314512, -59.136303),
        LatLng(-37.313361, -59.136993),
        LatLng(-37.312271, -59.137602),
        LatLng(-37.311015, -59.138427),
        LatLng(-37.310929, -59.138250),
        LatLng(-37.310596, -59.137509),
        LatLng(-37.310255, -59.136695),
        LatLng(-37.309708, -59.135214),
        LatLng(-37.309167, -59.135896),
        LatLng(-37.308228, -59.137065),
        LatLng(-37.307349, -59.138192),
        LatLng(-37.306432, -59.139356),
        LatLng(-37.305518, -59.140527),
        LatLng(-37.304618, -59.141710),
        LatLng(-37.303707, -59.142855),
        LatLng(-37.302790, -59.141731),
        LatLng(-37.301845, -59.140580),
        LatLng(-37.300932, -59.139451),
        LatLng(-37.299997, -59.138319),
        LatLng(-37.299075, -59.137161),
        LatLng(-37.298141, -59.136035),
        LatLng(-37.297254, -59.137194),
        LatLng(-37.298151, -59.138341),
        LatLng(-37.299078, -59.139477),
        LatLng(-37.300015, -59.140620),
        LatLng(-37.300934, -59.141766),
        LatLng(-37.300369, -59.142495),
        LatLng(-37.299825, -59.143235),
        LatLng(-37.299140, -59.144066),
        LatLng(-37.298240, -59.145246),
        LatLng(-37.297318, -59.146391),
        LatLng(-37.296433, -59.147544),
        LatLng(-37.295808, -59.148339),
        LatLng(-37.295477, -59.148733),
        LatLng(-37.295057, -59.149315),
        LatLng(-37.294632, -59.149857),
        LatLng(-37.294111, -59.150528),
        LatLng(-37.293671, -59.151043),
        LatLng(-37.292771, -59.152198),
        LatLng(-37.291892, -59.153363),
        LatLng(-37.290983, -59.154522),
        LatLng(-37.290033, -59.155706),
        LatLng(-37.291008, -59.156845),
        LatLng(-37.291958, -59.157931),
        LatLng(-37.291098, -59.159077),
        LatLng(-37.290152, -59.160239),
        LatLng(-37.290786, -59.161003),
        LatLng(-37.291328, -59.161700),
        LatLng(-37.292028, -59.162489),
        LatLng(-37.292973, -59.163702)
    )
    public val RECORRIDO_ROJO = listOf<LatLng>(
        LatLng(-37.29297368502808, -59.1636997461319),
        LatLng(-37.292016, -59.162512),
        LatLng(-37.290782, -59.161012),
        LatLng(-37.290173, -59.160275),
        LatLng(-37.291095, -59.159088),
        LatLng(-37.291959, -59.157930),
        LatLng(-37.290999, -59.156847),
        LatLng(-37.290068, -59.155712),
        LatLng(-37.290958, -59.154531),
        LatLng(-37.291852, -59.153367),
        LatLng(-37.292774, -59.152196),
        LatLng(-37.293656, -59.151052),
        LatLng(-37.294623, -59.149852),
        LatLng(-37.295501, -59.148728),
        LatLng(-37.296442, -59.147552),
        LatLng(-37.297317, -59.146400),
        LatLng(-37.298266, -59.147536),
        LatLng(-37.299148722427894, -59.14633512496949),
        LatLng(-37.298244049264056, -59.1452407836914),
        LatLng(-37.299128, -59.144101),
        LatLng(-37.299792, -59.143235),
        LatLng(-37.300355, -59.142527),
        LatLng(-37.30093243291965, -59.141786098480225),
        LatLng(-37.30182853945069, -59.1428804397583),
        LatLng(-37.30274170369332, -59.141721725463874),
        LatLng(-37.301824, -59.140634),
        LatLng(-37.300882, -59.139460),
        LatLng(-37.300007, -59.138312),
        LatLng(-37.299046307144216, -59.13716197013854),
        LatLng(-37.299989, -59.138293),
        LatLng(-37.300870, -59.137177),
        LatLng(-37.29997657418463, -59.136003255844116),
        LatLng(-37.300865, -59.137194),
        LatLng(-37.301810, -59.138274),
        LatLng(-37.302787, -59.139422),
        LatLng(-37.303654856847984, -59.14055228233337),
        LatLng(-37.304569, -59.139404),
        LatLng(-37.305489663775134, -59.13821339607238),
        LatLng(-37.306441, -59.139362),
        LatLng(-37.3073244259338, -59.140498638153076),
        LatLng(-37.308225, -59.139358),
        LatLng(-37.309142, -59.138203),
        LatLng(-37.310302618956115, -59.13666844367981),
        LatLng(-37.31060128510027, -59.137451648712165),
        LatLng(-37.31090848332505, -59.13825631141663),
        LatLng(-37.31101941570884, -59.138438701629646),
        LatLng(-37.312255, -59.137656),
        LatLng(-37.313332, -59.137136),
        LatLng(-37.314491, -59.136294),
        LatLng(-37.315680, -59.135611),
        LatLng(-37.316860, -59.134965),
        LatLng(-37.318012, -59.134181),
        LatLng(-37.319206, -59.133543),
        LatLng(-37.320352, -59.132990),
        LatLng(-37.321744950784435, -59.132108688354485),
        LatLng(-37.322286, -59.133693),
        LatLng(-37.322862, -59.135147),
        LatLng(-37.323419, -59.136639),
        LatLng(-37.32399737465876, -59.138055145740516),
        LatLng(-37.325136, -59.137424),
        LatLng(-37.326295, -59.136711),
        LatLng(-37.327448, -59.136018),
        LatLng(-37.3289200509023, -59.13516640663147),
        LatLng(-37.329409, -59.136521),
        LatLng(-37.329858479441874, -59.13768231868744),
        LatLng(-37.331016, -59.136976),
        LatLng(-37.332142, -59.136308),
        LatLng(-37.333343, -59.135588),
        LatLng(-37.334503, -59.134908),
        LatLng(-37.335795, -59.134129),
        LatLng(-37.336929, -59.133471),
        LatLng(-37.338089, -59.132744),
        LatLng(-37.3393210230564, -59.13199335336685),
        LatLng(-37.338788, -59.130609),
        LatLng(-37.338460, -59.129632),
        LatLng(-37.337878, -59.128172),
        LatLng(-37.33730150877836, -59.126655757427216),
        LatLng(-37.33847441174888, -59.12592351436614),
        LatLng(-37.339647296403086, -59.1252475976944),
        LatLng(-37.339105, -59.123828),
        LatLng(-37.33849147202058, -59.12220597267151),
        LatLng(-37.337360, -59.122928),
        LatLng(-37.33614564829625, -59.123589992523186),
        LatLng(-37.335572, -59.122100),
        LatLng(-37.335013, -59.120636),
        LatLng(-37.334574, -59.119469),
        LatLng(-37.333926, -59.117757),
        LatLng(-37.333355, -59.116160),
        LatLng(-37.332792, -59.114762),
        LatLng(-37.331576, -59.115438),
        LatLng(-37.330440725802326, -59.11607176065444),
        LatLng(-37.329914, -59.114685),
        LatLng(-37.329301822108825, -59.11305427551269),
        LatLng(-37.33047484999118, -59.11238104104996)

    )
}

