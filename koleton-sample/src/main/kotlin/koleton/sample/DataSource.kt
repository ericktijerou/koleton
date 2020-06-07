package koleton.sample

class DataSource{
    companion object{
        fun generateDataSet(): List<Journey>{
            return listOf(
                Journey(
                    date = "Tuesday, 21 April, 01:36 PM",
                    icon = R.drawable.ic_car_coupe,
                    address = "1600 Amphitheatre Parkway Mountain View, CA 94043",
                    price = "$22.24"
                ),
                Journey(
                    date = "Monday, 11 March, 08:17 PM",
                    icon = R.drawable.ic_car_sedan,
                    address = "1600 Seaport Boulevard Redwood City, CA 94063",
                    price = "$18.56"
                ),
                Journey(
                    date = "Saturday, 29 February, 10:22 AM",
                    icon = R.drawable.ic_car_minivan,
                    address = "803 11th Avenue Sunnyvale, CA 94089",
                    price = "$35.32"
                )
            )
        }
    }
}