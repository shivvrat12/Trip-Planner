const express = require('express');
const router = express.Router();
const { registerUser, loginUser, getUserProfile, getCollaboratorAndCreatorNames } = require('../controllers/userController');
const { protect } = require('../middleware/authMiddleware');

router.post('/register', registerUser);
router.post('/login', loginUser);
router.get('/profile', protect, getUserProfile);
router.post('/collaborator-names', getCollaboratorAndCreatorNames);

module.exports = router;
