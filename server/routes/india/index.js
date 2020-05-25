var router = require('express').Router()
var fetch = require('node-fetch')

router.get('/total',(req,res)=>{

    fetch('https://api.covid19india.org/data.json').then(result => result.json())
            .then(data => {
                res.status(200).json(data['cases_time_series'])
            }).catch(e=>{
                res.status(500).json({
                    msg:'server internal error , retry again later ...'
                })
            })
})

router.get('/allstates',(req,res)=>{

    fetch('https://api.covid19india.org/data.json').then(result => result.json())
            .then( data => {
                res.status(200).json(data['statewise'])
            }).catch((e)=>{
                res.status(500).json({
                    msg: 'error while fetching data retry later ... '
                })
            })
})

router.get('/state',(req,res)=>{

    const state = req.query.state

    if(state != null){
        fetch('https://api.covid19india.org/state_district_wise.json').then(result => result.json())
                .then( data =>{  
                    var filtered_data = []
                    const districts = data[state]["districtData"]

                    Object.keys(districts).forEach( (key)=>{

                        districtData = districts[key]

                        node = {
                            district : key,
                            ... districtData
                            
                        }

                        filtered_data.push(node)
                    })

                    res.status(200).json(filtered_data)

                }).catch((e)=>{
                    console.log(e)
                    res.status(500).json({
                        msg: 'error while fetching data retry later ... '
                    })
                })
    }else{
        console.log("here error")
        res.status(400).json({
            msg : 'Pass state Code '
        })
    }
})

module.exports = router