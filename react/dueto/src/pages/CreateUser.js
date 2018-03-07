import React, { Component } from 'react'
import {Input, TextField, Button, Typography} from 'material-ui'
//import {createUser} from '../utils/fetchData.js'

class CreateUser extends Component {
  constructor() {
    super()

    this.state = {
      name: undefined,
      password: undefined,
      repassword: undefined,
      username: undefined, 
      bio: undefined, 
      avatar: undefined,
      age: undefined,
      loc: undefined 
    }
  }

  textChange = (event) => { 
    this.setState({[event.target.name]: event.target.value})
  }

  create = () => {
    /*
    createUser(this.state.username, this.state.password)
      .then(data => {})
      .catch(error => {})
    */
  }

  render() {
    return (
      <div>
        <div>
          <TextField
            label="Name"
            name="name"
            value={this.state.name}
            onChange={this.textChange}
          />
          <TextField
            label="Username"
            name="username"
            value={this.state.username}
            onChange={this.textChange}
          />
          <TextField
            label="Biography"
            name="bio"
            value={this.state.bio}
            onChange={this.textChange}
          />
          <TextField
            label="Age"
            name="age"
            value={this.state.age}
            onChange={this.textChange}
          />
          <TextField
            label="Location"
            name="loc"
            value={this.state.loc}
            onChange={this.textChange}
          />
          <TextField
            label="Password"
            name="password"
            type="password"
            value={this.state.password}
            onChange={this.textChange}
          />
          <TextField
            label="Retype Password"
            name="repassword"
            type="password"
            value={this.state.repassword}
            onChange={this.textChange}
          />
        </div>
        <Typography>Account Avatar</Typography>
        <Input type="file" value={this.state.videoName} onChange={this.videoChange}/>
        <Button onClick={this.create}>Create Account</Button>
        <Button onClick={this.cancel}>Cancel</Button>
      </div>
    );
  }
}

export default CreateUser;
