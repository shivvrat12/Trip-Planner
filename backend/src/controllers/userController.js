const User = require('../models/User');
const jwt = require('jsonwebtoken');

// Generate JWT
const generateToken = (id) => {
    return jwt.sign({ id }, process.env.JWT_SECRET, { expiresIn: '30d' });
};

// @desc Register a new user
// @route POST /api/users/register
const registerUser = async (req, res) => {
    const { name, email, password } = req.body;

    try {
        const userExists = await User.findOne({ email });

        if (userExists) {
            return res.status(400).json({ message: 'User already exists' });
        }

        const user = await User.create({ name, email, password });

        if (user) {
            res.status(201).json({
                _id: user.id,
                name: user.name,
                email: user.email,
                token: generateToken(user.id),
            });
        } else {
            res.status(400).json({ message: 'Invalid user data' });
        }
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
};

// @desc Authenticate user & get token
// @route POST /api/users/login
const loginUser = async (req, res) => {
    const { email, password } = req.body;

    try {
        const user = await User.findOne({ email });

        if (user && (await user.matchPassword(password))) {
            res.json({
                _id: user.id,
                name: user.name,
                email: user.email,
                token: generateToken(user.id),
            });
        } else {
            res.status(401).json({ message: 'Invalid email or password' });
        }
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
};

// @desc Get user profile
// @route GET /api/users/profile
// @access Private
const getUserProfile = async (req, res) => {
    const user = await User.findById(req.user.id);

    if (user) {
        res.json({
            _id: user.id,
            name: user.name,
            email: user.email,
        });
    } else {
        res.status(404).json({ message: 'User not found' });
    }
};

const getCollaboratorAndCreatorNames = async (req, res) => {
    const { collaboratorIds, creatorId } = req.body; // Expect collaborator and creator IDs in the request body

    if (!collaboratorIds || !Array.isArray(collaboratorIds) || !creatorId) {
        return res.status(400).json({ message: 'Invalid collaborator or creator IDs' });
    }

    try {
        // Fetch the creator's name
        const creator = await User.findById(creatorId);
        if (!creator) {
            return res.status(404).json({ message: 'Creator not found' });
        }

        // Fetch collaborators' names
        const collaborators = await Promise.all(
            collaboratorIds.map(async (id) => {
                const user = await User.findById(id);
                return user ? user.name : null; // Return null if the user is not found
            })
        );

        res.json({
            creator: creator.name,
            collaborators: collaborators.filter(Boolean) // Remove null values
        });
    } catch (error) {
        console.error('Error fetching names:', error);
        res.status(500).json({ message: 'Server error' });
    }
};


module.exports = { registerUser, loginUser, getUserProfile, getCollaboratorAndCreatorNames};
