var router = require('express').Router()

router.use('/world',require('./world'))
router.use('/india',require('./india'))
router.use('/news',require('./news'))

module.exports = router 