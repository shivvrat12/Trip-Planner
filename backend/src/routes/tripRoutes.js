const express = require('express');
const router = express.Router();
const { createTrip, getTrips, updateTrip, inviteToTrip } = require('../controllers/tripController');
const { protect } = require('../middleware/authMiddleware');

router.route('/').post(protect, createTrip).get(protect, getTrips);
router.route('/:id').put(protect, updateTrip);
router.put('/:id/invite', protect, inviteToTrip);


module.exports = router;
