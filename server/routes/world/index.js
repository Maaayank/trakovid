var router = require('express').Router()
var fetch = require('node-fetch')


router.get('/all',(req,res)=>{

    fetch('https://api.covid19api.com/summary').then(result => 
        result.json()).then( data => {
            data_res = {
                global : data["Global"],
                countries : data["Countries"]
            }
        res.status(200).json(data_res)
    }).catch( e=>{
        res.status(500).json({
            msg : 'server internal error  , retry later '
        })
    })

})

router.get('/total',(req,res)=>{

    fetch('https://api.covid19api.com/summary').then(result => 
        result.json()).then( data => {
        res.status(200).json(data['Global'])
    }).catch( e=>{
        res.status(500).json({
            msg : 'server internal error  , retry later '
        })
    })
})

router.get('/countries',(req,res)=>{

    fetch('https://api.covid19api.com/summary').then(result => result.json())
            .then(data => {
                res.status(200).json(data['Countries'])
            }).catch( e=>{
                res.status(500).json({
                    msg : 'server internal error  , retry later '
                })
            })
})

router.get('/country',(req,res)=>{

    const country = req.query.country;

    if(country != null ){

        fetch(`https://api.covid19api.com/dayone/country/${country}`).then(result => result.json())
                .then( data => {
                    mergeDataValues(data)
                        .then((filtered_data)=> res.status(200).json(filtered_data))
                        .catch((e)=>{
                        res.status(500).json({
                            msg : 'server internal error , retry again later '
                        })
                    })
                }).catch( e=>{
                    console.log(e)
                    res.status(500).json({
                        msg : 'server internal error , retry again later '
                    })
                })
    }else {
        res.status(400).json({
            msg : 'invalid request parameters'
        })
    }
})

const  mergeDataValues = (data) => {
    return new Promise( (resolve,reject) => {

        temp_filtered = {}
        filtered_data = []
        console.log(data)
        data.forEach((stat)=>{

            try{

                temp_stat = temp_filtered[stat["Date"]]

                temp_filtered[stat["Date"]] = {

                    Country : temp_stat["Country"],
                    CountryCode : temp_stat["CountryCode"],
                    Confirmed : temp_stat["Confirmed"] + stat["Confirmed"],
                    Deaths : temp_stat["Deaths"] + stat["Deaths"],
                    Recovered : temp_stat["Recovered"] + stat["Recovered"],
                    Date : temp_stat["Date"]
                }

            }catch(e){

                temp_filtered[stat["Date"]] = {

                    Country : stat["Country"],
                    CountryCode : stat["CountryCode"],
                    Confirmed : stat["Confirmed"],
                    Deaths :stat["Deaths"],
                    Recovered : stat["Recovered"],
                    Date : stat["Date"]
                }
            }
        })

        console.log(temp_filtered)
        Object.keys(temp_filtered).forEach((key) => {

            stat = temp_filtered[key]
            node = {
                    country : stat["Country"],
                    countryCode : stat["CountryCode"],
                    confirmed : stat["Confirmed"],
                    deaths :stat["Deaths"],
                    recovered : stat["Recovered"],
                    date : stat["Date"]
            }

            filtered_data.push(node)

        })

        resolve(filtered_data)
    });
}

module.exports = router