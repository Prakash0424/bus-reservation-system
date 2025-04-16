const API_BASE_URL = "http://localhost:8080";  // Change based on your backend

// Fetch Available Buses
function fetchBuses() {
    fetch(`${API_BASE_URL}/buses`)
        .then(response => response.json())
        .then(data => {
            let busList = document.getElementById("busList");
            busList.innerHTML = "<h3>Available Buses</h3>";
            data.forEach(bus => {
                busList.innerHTML += `<p>Bus No: ${bus.busNo} | Route: ${bus.fromToLocation} | AC: ${bus.ac ? 'Yes' : 'No'} | Seats: ${bus.capacity}</p>`;
            });
        })
        .catch(error => console.error("Error fetching buses:", error));
}

// Booking a Ticket
document.getElementById("bookingForm").addEventListener("submit", function(event) {
    event.preventDefault();

    const bookingData = {
        busNo: document.getElementById("busNo").value,
        passengerName: document.getElementById("passengerName").value,
        numOfPassengers: document.getElementById("numPassengers").value,
        travelDate: document.getElementById("travelDate").value
    };

    fetch(`${API_BASE_URL}/bookings`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(bookingData)
    })
    .then(response => response.json())
    .then(data => {
        alert("Booking Successful! Your Booking ID: " + data.bookingNo);
    })
    .catch(error => console.error("Error booking ticket:", error));
});

// View Booking History
function viewBookingHistory() {
    let userId = prompt("Enter your User ID:");
    fetch(`${API_BASE_URL}/history/${userId}`)
        .then(response => response.json())
        .then(data => {
            let historyDiv = document.getElementById("bookingHistory");
            historyDiv.innerHTML = "<h3>Your Bookings</h3>";
            data.forEach(booking => {
                historyDiv.innerHTML += `<p>Booking ID: ${booking.bookingNo} | Bus No: ${booking.busNo} | Date: ${booking.travelDate} | Passengers: ${booking.numOfPassengers}</p>`;
            });
        })
        .catch(error => console.error("Error fetching booking history:", error));
}

// Cancel a Booking
function cancelBooking() {
    let bookingId = document.getElementById("cancelBookingId").value;

    fetch(`${API_BASE_URL}/bookings/${bookingId}`, { method: "DELETE" })
        .then(response => {
            if (response.ok) {
                alert("Booking Canceled Successfully!");
            } else {
                alert("Error canceling booking!");
            }
        })
        .catch(error => console.error("Error canceling booking:", error));
}
