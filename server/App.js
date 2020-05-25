const express = require('express')
const cors = require('cors')
const bodyParser = require('body-parser')

require("dotenv").config()

const port = process.env.PORT || 8080;

const app = express()

app.use(bodyParser.urlencoded({
    extended:true
}))

app.use(cors({origin:true,credentials:true}))
app.use(bodyParser.json())

app.get('/',(req,res)=>{
    res.status(200).json({
        success:true,
        msg:'home page'
    })
})

app.use('/api',require('./routes'))

app.use((req,res)=>{

    res.status(404).json({
        success:false,
        msg:'page not found'
    })
})

app.listen(port,()=>{
    console.log("Server starting on port " + port)
})