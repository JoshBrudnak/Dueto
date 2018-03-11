import React, { Component } from 'react'
import { TextField, Button } from 'material-ui'
import {loginUser} from '../utils/fetchData.js'

class Login extends Component {
  constructor() {
    super()

    this.state = {
      password: "password",
      username: "joeshmow"
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
      .then(data => { 
        window.location = "/home"
        console.log(window)
      })
      .catch(error => {
      })

    window.location = "/home"
    console.log(window)
  }

  render() {
    const loginStyle = {}
    return (
      <div>
        <div>
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
        <Button onClick={this.login}>Login</Button>
        <Button onClick={this.cancel}>Cancel</Button>
      </div>
    );
  }
}

export default Login;
