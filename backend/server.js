const express = require("express");
require('dotenv').config();
const connectDB = require("./src/config/db");
const userRoutes = require('./src/routes/userRoutes'); // Import routes
const tripRoutes = require('./src/routes/tripRoutes'); // Import trip routes
const expenseRoutes = require('./src/routes/expenseRoutes'); //Expenses Routes


const app = express();

//middleware
app.use(express.json());
app.use(express.urlencoded({extended:true}));
connectDB();

app.use('/api/users', userRoutes);
app.use('/api/trips', tripRoutes);
app.use('/api/expenses', expenseRoutes);

const PORT = process.env.PORT || 5000;

app.listen(PORT, () => {
    console.log('Server is running on port ${PORT}');

});
