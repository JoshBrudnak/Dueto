import React, { Component } from 'react'
import {Input, TextField, Button, Typography, Paper} from '@material-ui/core'
import {loginUser, addUser, addAvatar, getLocation} from '../utils/fetchData.js'
import {Link} from "react-router-dom"

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
      email: undefined,
      avatarPath: undefined, 
      avatarName: "No file selected",
      nameError: false,
      emailError: false,
      usernError: false,
      passError: false,
      repassError: false
    }
  }

  textChange = (event) => { 
    this.setState({[event.target.name]: event.target.value})
  }

  create = () => {
    const s = this.state
    
    if(s.name === undefined || s.name === "") {
      this.setState({nameError: true})
      return
    }
    if(s.username === undefined || s.username === "") {
      this.setState({usernError: true})
      return
    }
    if(s.password === undefined || s.password === "") {
      this.setState({passError: true})
      return
    }
    if(s.repassword === undefined || s.repassword === "") {
      this.setState({repassError: true})
      return
    }
    if(s.email === undefined || s.email === "") {
      this.setState({emailError: true})
      return
    }
 
    const ns = this.state
    if(ns.nameError || ns.usernError || ns.passError || ns.repassError || ns.emailError) {
      return
    }

    getLocation()
      .then(data => {
        const userdata = {
          Name: s.name,
          Password: s.password,
          Repassword: s.repassword,
          Username: s.username,
          Email: s.email,
          Bio: s.bio,
          Age: s.age,
          Loc: JSON.stringify(data)
        }

        addUser(userdata)
          .then(data => {
            loginUser(this.state.username, this.state.password)
             .then(data => { 
               const {avatarName, avatar} = this.state
    
               let formData = new FormData()
               formData.append("file", avatar)
               formData.append("name", avatarName)

               addAvatar(formData)
                 .then(data => { 
                    window.location = "/home"
                 })
                 .catch(error => {
                   console.error(error)
                 })
             })
             .catch(error => {
               console.error(error)
             })
           })
         .catch(error => { 
           console.error(error)
         })
       })
       .catch(error => { 
          console.error(error)
       })

       
  }

  avatarChange = (event) => {
    let file = event.target.files[0]
    this.setState({avatarPath: event.target.value, avatarName: file.name, avatar: file})
  }

  profilePicture = () => {
    document.getElementById("profilePic").click()
  }

  cancel = () => {
    document.getElementById("cancel").click()
  }
     

  render() {
    const fieldStyle = {
      marginTop: 10
    }

    return (
      <div style={{backgroundColor: "#e8e8e8", height: "-webkit-fill-available", display: "flex", flexDirection: "column", alignItems: "center"}}>
        <Paper style={{display: "flex", flexDirection: "column", width: 350, margin: 40, padding: 40}}>
          <div style={{display: "flex", justifyContent: "center", alignItems: "center"}}>
            <img style={{width: 100, height: 100}} src="/resource/dueto.png" alt="Dueto"/>
          </div>
          <TextField
            label="Name"
            name="name"
            error={this.state.nameError}
            style={fieldStyle}
            value={this.state.name}
            onChange={this.textChange}
          />
          <TextField
            label="Username"
            name="username"
            error={this.state.usernError}
            style={fieldStyle}
            value={this.state.username}
            onChange={this.textChange}
          />
          <TextField
            label="Biography"
            name="bio"
            style={fieldStyle}
            value={this.state.bio}
            onChange={this.textChange}
          />
          <TextField
            label="Age"
            name="age"
            style={fieldStyle}
            value={this.state.age}
            onChange={this.textChange}
          />
          <TextField
            label="E-mail"
            name="email"
            error={this.state.emailError}
            style={fieldStyle}
            value={this.state.email}
            onChange={this.textChange}
          />
          <TextField
            label="Password"
            name="password"
            type="password"
            error={this.state.passError}
            style={fieldStyle}
            value={this.state.password}
            onChange={this.textChange}
          />
          <TextField
            label="Retype Password"
            name="repassword"
            type="password"
            error={this.state.repassError}
            style={fieldStyle}
            value={this.state.repassword}
            onChange={this.textChange}
          />
          <div style={{display: "flex", flexDirection: "row", alignItems: "baseline", marginTop: 20}}>
            <Button onClick={this.profilePicture}>Profile Picture</Button>
            <Typography style={{marginLeft: 10}}>{this.state.avatarName}</Typography> 
          </div>
          <Input 
            id="profilePic"
            type="file"
            allow=".jpeg"
            style={{visibility: "collapse"}} 
            value={this.state.avatarPath} 
            onChange={this.avatarChange}
          />
          <div style={{marginTop: 10}}>
            <Button onClick={this.create}>Create Account</Button>
            <Button onClick={this.cancel}>Cancel</Button>
          </div>
        </Paper>
        <Link id="cancel" to="/login"/>
      </div>
    );
  }
}

export default CreateUser;
