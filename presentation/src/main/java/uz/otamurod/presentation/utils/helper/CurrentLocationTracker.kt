package uz.otamurod.presentation.utils.helper

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Build
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.*
import kotlin.coroutines.resume

class CurrentLocationTracker(private val applicationContext: Context) {
    private val locationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(applicationContext)

    suspend fun getLocation(): Location? {
        val hasAccessFineLocationPermission = checkHasAccessFineLocationPermission()
        val hasAccessCoarseLocationPermission = checkHasAccessCoarseLocationPermission()

        val isGpsEnabled = checkGpsEnabled()

        if (!hasAccessCoarseLocationPermission || !hasAccessFineLocationPermission || !isGpsEnabled) {
            return null
        }

        return suspendCancellableCoroutine { cancellableContinuation ->
            locationClient.lastLocation.apply {
                if (isComplete) {
                    if (isSuccessful) {
                        cancellableContinuation.resume(result)
                    } else {
                        cancellableContinuation.resume(null)
                    }
                    return@suspendCancellableCoroutine
                }
                addOnSuccessListener {
                    cancellableContinuation.resume(it)
                }
                addOnFailureListener {
                    cancellableContinuation.resume(null)
                }
                addOnCanceledListener {
                    cancellableContinuation.cancel()
                }
            }
        }
    }

    fun checkHasAccessFineLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            applicationContext, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun checkHasAccessCoarseLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            applicationContext, Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun checkGpsEnabled(): Boolean {
        val locationManager =
            applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    fun getAddressName(latitude: Double, longitude: Double): String {
        var addressText = ""

        Geocoder(
            applicationContext, Locale.getDefault()
        ).apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                getFromLocation(latitude, longitude, 1, object : Geocoder.GeocodeListener {
                    override fun onGeocode(addresses: MutableList<Address>) {
                        addresses.firstOrNull()?.let { address ->
                            addressText = getFormattedAddressName(address)
                        }
                    }

                    override fun onError(errorMessage: String?) {
                        super.onError(errorMessage)
                        addressText = ""
                    }
                })
            } else {
                try {
                    @Suppress("DEPRECATION")
                    getFromLocation(latitude, longitude, 1)?.firstOrNull()?.let { address ->
                        addressText = getFormattedAddressName(address)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        return addressText
    }

    private fun getFormattedAddressName(address: Address): String {
        return buildString {
            append(address.subAdminArea)
            append(", ")
            append(address.countryName)
        }
    }
}