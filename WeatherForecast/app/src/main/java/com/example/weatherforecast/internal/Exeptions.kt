package com.example.weatherforecast.internal

import java.io.IOException
import java.lang.Exception

class NoConnectivityExeption: IOException()
class LocationPermissionNotGrantedException: Exception()