package iex

data class Sector(
        val name: String = "",
        val performance: Double = 0.0,
        val lastUpdated: Long = 0
        ){

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}