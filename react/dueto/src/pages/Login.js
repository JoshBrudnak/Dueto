import React, { Component } from 'react'
import { TextField, Button, Paper, Typography } from 'material-ui'
import {Link} from 'react-router-dom'
import {loginUser} from '../utils/fetchData.js'

class Login extends Component {
  constructor() {
    super()

    this.state = {
      password: "",
      username: "",
      servError: false,
      error: false
    }
  }

  usernameChange = (event) => { 
    this.setState({username: event.target.value})
  }

  passwordChange = (event) => { 
    this.setState({password: event.target.value})
  }

  login = () => {
    loginUser(this.state.username, this.state.password)
      .then(stat => { 
        if(stat >= 400 && stat < 404) {
          this.setState({error: true})
        }
        else if(stat >= 404) {
          this.setState({servError: true})
        }
        else {
          window.location = "/home"
        }
      })
      .catch(error => {
        console.error(error) 
      })
  }

  errorText = () => {
    if(this.state.error) {
      return (
        <Typography style={{color: "red"}}>Incorrect username or password</Typography>
      )
    }
  }

  render() {
    return (
      <div style={{display: "flex", flexDirection: "column", alignItems: "center"}}>
        <Paper style={{padding: 40, margin: 40, width: 250}}>
          <Typography style={{fontSize: "large"}} variant="heading1">Dueto</Typography>
          {this.errorText()}
          <div style={{display: "flex", flexDirection: "column"}}>
            <TextField
              label="UserName"
              margin="normal"
              value={this.state.username}
              onChange={this.usernameChange}
            />
            <TextField
              label="Password"
              type="password"
              margin="normal"
              value={this.state.password}
              onChange={this.passwordChange}
            />
          </div>
          <div style={{paddingTop: 10}}>
            <Button onClick={this.login}>Login</Button>
            <Button onClick={this.cancel}>Cancel</Button>
          </div>
        </Paper>
        <div style={{display: "flex"}}>
          <Typography style={{marginLeft: 10}}>New to Dueto?</Typography>
          <Link to="/createuser"> create account</Link>
        </div>
      </div>
    )
  }
}

export default Login;
