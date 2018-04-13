import React, { Component } from 'react'
import {Input, Button, TextField, Paper, Typography} from 'material-ui'
import Header from '../component/Header.js'
import GenreDialog from '../component/GenreDialog.js'
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
      genre: "",
      genreText: "No genre selected",
      chGenre: false
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

    const {video, name, desc, genre} = this.state
    let formData = new FormData()
    formData.append("file", video)
    formData.append("name", name)
    formData.append("desc", desc)
    formData.append("genre", genre)

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
    this.setState({[event.target.name]: event.target.value});
  }

  cancel = (event) => {
    document.getElementById("done").click()
  }

  changeGenre = () => {
    this.setState({chGenre: true})
  }
 
  setGenre = (value) => {
    this.setState({genre: value, genreText: value, chGenre: false})
  }

  closeDialog = () => {
    this.setState({chGenre: false})
  }

  render() {
    return (
      <div>
      <Header/>
      <div style={{height: "-webkit-fill-available", backgroundColor: "#e8e8e8", padding: 20, display: "flex", flexDirection: "column", alignItems: "center"}}>
        <Paper style={{height: "fit-content", display: "flex", flexDirection: "column", width: 350, padding: 40}}>
          <Typography style={{fontSize: "large"}}>New Video</Typography>
          <div>
            <TextField
              label="Video Name"
              name="name"
              value={this.state.name}
              onChange={this.changeValue}
            />
            <TextField
              label="Description"
              name="desc"
              value={this.state.desc}
              onChange={this.changeValue}
            />
            <div style={{display: "flex", flexDirection: "row", alignItems: "baseline", marginTop: 20}}>
              <Button onClick={this.changeGenre}>Genre</Button>
              <Typography style={{marginLeft: 10}}>{this.state.genreText}</Typography>
            </div>
            <div style={{display: "flex", flexDirection: "row", alignItems: "baseline", marginTop: 20}}>
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
            <GenreDialog curr={this.state.genre} open={this.state.chGenre} genre={this.setGenre} close={this.closeDialog}/>
            </div>
          </Paper>
        </div>
        <Link id="done" to="/profile"/>
      </div>
    )
  }
}

export default AddVideo;
