const Trip = require('../models/Trip');
const User = require('../models/User');

// @desc Create a new trip
// @route POST /api/trips
// @access Private
const createTrip = async (req, res) => {
    const { title, description, startDate, endDate } = req.body;

    try {
        const trip = await Trip.create({
            title,
            description,
            startDate,
            endDate,
            creator: req.user.id,
            collaborators: [req.user.id],
        });
        res.status(201).json(trip);
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
};

// @desc Get trips for a user
// @route GET /api/trips
// @access Private
const getTrips = async (req, res) => {
    try {
        const trips = await Trip.find({ collaborators: req.user.id });
        res.json(trips);
        console.log('Fetched trips:', trips);
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
};

const inviteToTrip = async (req, res) => {
    const { id } = req.params; // Trip ID
    const { email } = req.body; // Email of the user to invite
  
    try {
      // Find the trip by ID
      const trip = await Trip.findById(id);
      if (!trip) {
        return res.status(404).json({ message: 'Trip not found' });
      }
  
      // Find the user by email
      const user = await User.findOne({ email });
      if (!user) {
        return res.status(404).json({ message: 'User not found' });
      }
  
      // Add the user ID to the collaborators list if not already present
      if (!trip.collaborators.includes(user._id)) {
        trip.collaborators.push(user._id);
        await trip.save();
      }
  
      res.json({ message: `User ${email} invited successfully!` });
    } catch (error) {
      console.error('Error inviting to trip:', error);
      res.status(500).json({ message: 'Server error' });
    }
  };
  

// @desc Update a trip
// @route PUT /api/trips/:id
// @access Private
const updateTrip = async (req, res) => {
    try {
        const trip = await Trip.findById(req.params.id);

        if (!trip) {
            return res.status(404).json({ message: 'Trip not found' });
        }

        if (trip.creator.toString() !== req.user.id) {
            return res.status(401).json({ message: 'Not authorized to update this trip' });
        }

        const updatedTrip = await Trip.findByIdAndUpdate(req.params.id, req.body, {
            new: true,
            runValidators: true,
        });

        res.json(updatedTrip);
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
};



module.exports = { createTrip, getTrips, updateTrip, inviteToTrip};
