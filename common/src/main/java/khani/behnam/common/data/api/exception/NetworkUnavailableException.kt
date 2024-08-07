package khani.behnam.common.data.api.exception

import java.io.IOException

class NetworkUnavailableException(message: String = "No network available!") : IOException(message)
