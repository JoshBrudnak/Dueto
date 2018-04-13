import React, { Component } from "react"
import {Typography, Paper} from "material-ui"
import {Link} from "react-router-dom"

class Logout extends Component {
  render() {
    return (
      <div style={{backgroundColor: "#e8e8e8", height: "-webkit-fill-available", display: "flex", flexDirection: "column", alignItems: "center"}}>
        <Paper style={{margin: 40, padding: 40, display: "flex", flexDirection: "column", alignItems: "center"}}>
          <Link to="/login">
            <img style={{width: 150, height: 150, marginBottom: 20}} src="/resource/dueto.png" alt="Dueto"/>
          </Link>
          <Typography style={{marginBottom: 20, fontSize: "large"}}>Logout Successful</Typography>
          <Typography style={{fontSize: "large"}}>You are now logged out of Dueto</Typography>
        </Paper>
      </div>
    )
  }
}

export default Logout
