var router = require('express').Router()
var fetch = require('node-fetch')

router.get('/total',(req,res)=>{

    fetch('https://api.covid19api.com/summary').then(result => result.json())
            .then( data => {
                res.status(200).json(data['Global'])
            })
})

router.get('/countries',(req,res)=>{

    fetch('https://api.covid19api.com/summary').then(result => result.json())
            .then(data => {
                res.status(200).json(data['Countries'])
            })
})

router.get('/country',(req,res)=>{

    const country = req.body.country;

    if(country != null){

        fetch(`https://api.covid19api.com/dayone/country/${country}/status/confirmed`).then(result => result.json())
                .then( data => {
                    filtered_data = []
                    console.log(data)

                    data.forEach((stat)=>{

                        node = {
                            country : stat['Country'],
                            countryCode : stat['CountryCode'],
                            lat : stat['Lat'],
                            lon : stat['Lon'],
                            cases : stat['Cases'],
                            date : stat['Date']
                        }

                        filtered_data.push(node)
                        
                    })
                    // console.log(filtered_data)
                    res.status(200).json(filtered_data)
                })
    }else {
        res.status(400).json({
            msg : 'invalid request '
        })
    }
})

module.exports = router