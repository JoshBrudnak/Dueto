import React, { Component } from 'react'
import {Input, Button, TextField} from 'material-ui'
import Header from '../component/Header.js'
import {getProfileData, addVideo} from '../utils/fetchData.js'

class Profile extends Component {
  constructor() {
    super()
   
    this.state = {
      videoName: undefined,
      name: undefined,
      video: undefined,
      desc: "", 
    }
  }

  componentDidMount() {
    getProfileData(this.props.user)
      .then(data => {
      })
      .catch(error => {})
  }

  videoChange = (event) => {
    let file = event.target.files[0]
    this.setState({video: file}) 

    if (this.state.name === undefined) {
      this.setState({videoName: event.target.value, name: event.target.value})
    }
    else {
      this.setState({videoName: event.target.value})
    }
        
  }

  uploadVideo = () => {
    if (this.state.video === undefined) {
      //TODO: flag error  
    }

    const {video, name, desc} = this.state
    let formData = new FormData()
    formData.append("file", video)
    formData.append("name", name)
    formData.append("desc", desc)

    addVideo(formData)
      .then(data => {
      })
      .catch(error => {})
  }

  changeValue = (event) => {
    console.log(event.target.name)
    this.setState({[event.target.name]: event.target.value});
  }

  render() {
    return (
      <div>
        <Header/>
        <div>
          <TextField
            label="Video Name"
            name="name"
            margin="normal"
            value={this.state.username}
            onChange={this.changeValue}
          />
          <TextField
            label="Description"
            name="desc"
            margin="normal"
            value={this.state.username}
            onChange={this.changeValue}
          />
          <TextField
            label="Genre"
            name="genre"
            margin="normal"
            value={this.state.username}
            onChange={this.changeValue}
          />

          <label>profile page</label>
          <Input type="file" value={this.state.videoName} onChange={this.videoChange}/>
          <Button onClick={this.uploadVideo}>Upload</Button>
        </div>
      </div>
    )
  }
}

export default Profile;
