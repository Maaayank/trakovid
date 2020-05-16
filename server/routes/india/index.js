var router = require('express').Router()
var fetch = require('node-fetch')

router.get('/total',(req,res)=>{

    fetch('https://api.covid19india.org/data.json').then(result => result.json())
            .then(data => {
                res.status(200).json(data['cases_time_series'])
            })
})

router.get('/allstate',(req,res)=>{

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

    const stateCode = req.body.stateCode

    if(stateCode != null){
        fetch('https://api.covid19india.org/state_district_wise.json').then(result => result.json())
                .then( data =>{  
                    data.forEach( state_data=> {
                        if(state_data['statecode'] == stateCode){
                            res.status(200).json(state_data)
                        }
                    })

                    res.status(400).json({
                        msg : 'incorrect state Code '
                    })
                }).catch((e)=>{
                    res.status(500).json({
                        msg: 'error while fetching data retry later ... '
                    })
                })
    }else{

        res.status(400).json({
            msg : 'Pass state Code '
        })
    }
})

module.exports = router