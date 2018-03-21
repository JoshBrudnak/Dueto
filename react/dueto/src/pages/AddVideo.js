import React, { Component } from 'react'
import {Input, Button, TextField, Paper, Typography} from 'material-ui'
import Header from '../component/Header.js'
import {Link} from "react-router-dom"
import {getProfileData, addVideo} from '../utils/fetchData.js'

class AddVideo extends Component {
  constructor() {
    super()
   
    this.state = {
      videoName: undefined,
      videoTitle: "No file selected",
      name: undefined,
      video: undefined,
      error: false,
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
    this.setState({video: file, videoTitle: file.name}) 

    if (this.state.name === undefined) {
      this.setState({videoName: event.target.value, name: event.target.value})
    }
    else {
      this.setState({videoName: event.target.value})
    }
        
  }

  videoClick = () => {
    document.getElementById("videoFile").click()
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
        document.getElementById("done").click()
        window.location = "/home"
      })
      .catch(error => {
        this.setState({error: true})
      })
  }

  changeValue = (event) => {
    console.log(event.target.name)
    this.setState({[event.target.name]: event.target.value});
  }

  cancel = (event) => {
    document.getElementById("done").click()
  }

  render() {
    return (
      <div>
      <Header/>
      <div style={{margin: 20, display: "flex", flexDirection: "column", alignItems: "center"}}>
        <Paper style={{display: "flex", flexDirection: "column", width: 350, padding: 40}}>
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
            <div style={{display: "flex", flexDirection: "row", alignItems: "baseline", marginTop: 10}}>
              <Button onClick={this.videoClick}>Video</Button>
              <Typography style={{marginLeft: 10}}>{this.state.videoTitle}</Typography>
            </div>
            <Input 
              id="videoFile"
              type="file" 
              style={{visibility: "collapse"}}
              value={this.state.videoName} 
              onChange={this.videoChange}
            />
            <div style={{marginTop: 10}}>
              <Button onClick={this.uploadVideo}>Upload</Button>
              <Button onClick={this.cancel}>Cancel</Button>
            </div>
            </div>
          </Paper>
        </div>
        <Link id="done" to="/profile"/>
      </div>
    )
  }
}

export default AddVideo;
