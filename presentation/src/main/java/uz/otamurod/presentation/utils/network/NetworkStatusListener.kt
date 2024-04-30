package uz.otamurod.presentation.utils.network

interface NetworkStatusListener {
    fun onNetworkAvailable()
    fun onNetworkLost()
}