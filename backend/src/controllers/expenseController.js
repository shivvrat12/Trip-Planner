const Expense = require('../models/Expense');

// @desc Add an expense
// @route POST /api/expenses
// @access Private
const addExpense = async (req, res) => {
    const { description, amount, paidBy, sharedWith } = req.body;

    try {
        const expense = await Expense.create({
            trip: req.body.trip,
            description,
            amount,
            paidBy,
            sharedWith,
        });
        res.status(201).json(expense);
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
};

// @desc Get expenses for a trip
// @route GET /api/expenses
// @access Private
const getExpenses = async (req, res) => {
    try {
        const expenses = await Expense.find({ trip: req.query.trip });
        res.json(expenses);
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
};

module.exports = { addExpense, getExpenses };
