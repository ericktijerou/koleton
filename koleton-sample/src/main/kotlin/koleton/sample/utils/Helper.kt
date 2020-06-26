package koleton.sample.utils

import koleton.sample.R
import koleton.sample.model.Journey

class Helper {
    companion object {
        fun generateDataSet(): List<Journey> {
            return listOf(
                Journey(
                    id = "1",
                    date = "Tuesday, 21 April",
                    pickUpTime = "01:14 PM",
                    dropOffTime = "01:30 PM",
                    pickUpPoint = "1600 Amphitheatre Parkway Mountain View, CA 94043",
                    dropOffPoint = "1600 Seaport Boulevard Redwood City, CA 94063",
                    driverName = "Jared",
                    carName = "Nissan Altima",
                    basePrice = "$14.00",
                    servicePrice = "$1.24",
                    total = "$15.24",
                    paymentMethod = "∙∙∙∙ 7869",
                    driverImage = R.drawable.driver_one,
                    carIcon = R.drawable.ic_car_sedan,
                    mapImage = R.drawable.map_one
                ),
                Journey(
                    id = "2",
                    date = "Monday, 11 March",
                    pickUpTime = "08:17 PM",
                    dropOffTime = "08:45 PM",
                    pickUpPoint = "1600 Seaport Boulevard Redwood City, CA 94063",
                    dropOffPoint = "901 Cherry Avenue San Bruno, CA 94066",
                    driverName = "Alex",
                    carName = "BMW 528i",
                    basePrice = "$25.00",
                    servicePrice = "$1.56",
                    total = "$26.56",
                    paymentMethod = "∙∙∙∙ 4315",
                    driverImage = R.drawable.driver_two,
                    carIcon = R.drawable.ic_car_coupe,
                    mapImage = R.drawable.map_two
                ),
                Journey(
                    id = "3",
                    date = "Saturday, 29 February",
                    pickUpTime = "10:22 AM",
                    dropOffTime = "10:45 PM",
                    pickUpPoint = "901 Cherry Avenue San Bruno, CA 94066",
                    dropOffPoint = "345 Spear Street San Francisco, CA 94105",
                    driverName = "Albers",
                    carName = "Hyundai Santa Fe",
                    basePrice = "$33.00",
                    servicePrice = "$1.78",
                    total = "$34.78",
                    paymentMethod = "∙∙∙∙ 5642",
                    driverImage = R.drawable.driver_three,
                    carIcon = R.drawable.ic_car_minivan,
                    mapImage = R.drawable.map_three
                ),
                Journey(
                    id = "4",
                    date = "Tuesday, 21 April",
                    pickUpTime = "01:14 PM",
                    dropOffTime = "01:30 PM",
                    pickUpPoint = "1600 Amphitheatre Parkway Mountain View, CA 94043",
                    dropOffPoint = "1600 Seaport Boulevard Redwood City, CA 94063",
                    driverName = "Jared",
                    carName = "Nissan Altima",
                    basePrice = "$14.00",
                    servicePrice = "$1.24",
                    total = "$15.24",
                    paymentMethod = "∙∙∙∙ 7869",
                    driverImage = R.drawable.driver_one,
                    carIcon = R.drawable.ic_car_sedan,
                    mapImage = R.drawable.map_one
                ),
                Journey(
                    id = "5",
                    date = "Monday, 11 March",
                    pickUpTime = "08:17 PM",
                    dropOffTime = "08:45 PM",
                    pickUpPoint = "1600 Seaport Boulevard Redwood City, CA 94063",
                    dropOffPoint = "901 Cherry Avenue San Bruno, CA 94066",
                    driverName = "Alex",
                    carName = "BMW 528i",
                    basePrice = "$25.00",
                    servicePrice = "$1.56",
                    total = "$26.56",
                    paymentMethod = "∙∙∙∙ 4315",
                    driverImage = R.drawable.driver_two,
                    carIcon = R.drawable.ic_car_coupe,
                    mapImage = R.drawable.map_two
                ),
                Journey(
                    id = "6",
                    date = "Saturday, 29 February",
                    pickUpTime = "10:22 AM",
                    dropOffTime = "10:45 PM",
                    pickUpPoint = "901 Cherry Avenue San Bruno, CA 94066",
                    dropOffPoint = "345 Spear Street San Francisco, CA 94105",
                    driverName = "Albers",
                    carName = "Hyundai Santa Fe",
                    basePrice = "$33.00",
                    servicePrice = "$1.78",
                    total = "$34.78",
                    paymentMethod = "∙∙∙∙ 5642",
                    driverImage = R.drawable.driver_three,
                    carIcon = R.drawable.ic_car_minivan,
                    mapImage = R.drawable.map_three
                )
            )
        }
    }
}