import React, { Component } from 'react'
import {Avatar, Typography, Paper, IconButton, Tabs, Tab} from 'material-ui'
import Header from '../component/Header.js'
import {AddCircle, Settings} from 'material-ui-icons'
import {Link} from 'react-router-dom'
import VideoCard from '../component/VideoCard.js'
import {getProfileData, getSharedVideos} from '../utils/fetchData.js'

class Profile extends Component {
  constructor() {
    super()
   
    this.state = {
      name: "",
      username: "",
      desc: "",
      id: undefined,
      likes: undefined,
      followers: undefined,
      videos: [],
      reshares: [],
      videoPage: true,
      chatPage: false, 
      resharePage: false,
      avatarUrl: "" 
    }
  }

  componentDidMount() {
    getProfileData()
      .then(data => {
        let avUrl = "/api/avatar?artist=" + data.Id

        this.setState({
          name: data.Name,
          username: data.Username,
          desc: data.Desc,
          followers: data.FollowerCount,
          likes: data.LikeCount,
          videos: data.VideoList,
          avatarUrl: avUrl 
        })
      })
      .catch(error => {
        window.location = "/login"
      })

    getSharedVideos()
      .then(data => {
          console.log(data)
        this.setState({
          reshares: data.VideoCards,
        })
      })
      .catch(error => {
        console.error(error)
      })
  }

  tabChange = (event, value) => {
    switch(value) {
      case 0:
        this.setState({videoPage: true, resharePage: false})
        break
      case 1:
        this.setState({videoPage: false, resharePage: true})
        break
      default:
        this.setState({videoPage: true, resharePage: false})
        break
    }
  }

  edit = () => {
    document.getElementById("edit").click()
  }

  addVideo = () => {
    document.getElementById("newvideo").click()
  }

  getBody = () => {
    const videoCards = []
    if(this.state.videoPage) {
      if(this.state.videos !== null) {
        for(let i = 0; i < this.state.videos.length; i++) {
          const data = this.state.videos[i]
    
          videoCards.push(
            <VideoCard
              style={{margin: 40}}
              id={data.Id}
              artist={data.Artist.Id}
              desc={data.Desc}
              name={data.Title}
            />
          )
        }
      }
    }
    else if(this.state.resharePage) {
      if(this.state.reshares !== undefined) {
        for(let i = 0; i < this.state.reshares.length; i++) {
          const data = this.state.reshares[i]
    
          videoCards.push(
            <VideoCard
              style={{margin: 40}}
              id={data.Id}
              artist={data.Artist.Id}
              desc={data.Desc}
              name={data.Title}
            />
          )
        }
      }
    }
    else {
      videoCards.push(
        <Paper style={{height: "fit-content", padding: 20, margin: 20}}>
          <Typography style={{fontSize: "large"}}>This user has no videos</Typography>
        </Paper>
      )
    }
    
    return videoCards
  }

  render() {
    const bodyStyle = {
      backgroundColor: "#e8e8e8",
      display: "flex",
      flexDirection: "column",
      justifyContent: "flex-start"
    }

    return (
      <div>
        <Header/>
        <div style={bodyStyle}>
          <Paper style={{padding: 10, width: "-webkit-fill-available", display: "flex", flexDirection: "column", alignItems: "center"}}>
            <div style={{display: "flex", flexDirection: "row"}}> 
              <IconButton onClick={this.edit}>
                <Settings/>
              </IconButton>
              <Avatar src={this.state.avatarUrl} style={{width: 100, height: 100, marginBotton: 10}}/>
              <IconButton onClick={this.addVideo}>
                <AddCircle/>
              </IconButton>
            </div> 
            <Typography style={{fontSize: "large"}}>{this.state.name}</Typography> 
            <Typography style={{fontSize: "large"}}>{this.state.username}</Typography>
          </Paper>
          <Paper style={{display: "grid", alignItems: "center"}}>
          <div style={{gridColumn: 2}}>
            <Tabs onChange={this.tabChange}>
                <Tab style={{width: 100}} label="Video" />
                <Tab style={{width: 100}} label="Reshared" />
            </Tabs>
          </div>
          </Paper>
        </div>
        <div style={{height: "-webkit-fill-available", backgroundColor: "#e8e8e8", display: "flex", flexWrap: "wrap", justifyContent: "center"}}>
          {this.getBody()}
        </div>
        <Link id="edit" to="/editprofile" style={{visibility: "collapse"}}/>
        <Link id="newvideo" to="/addvideo" style={{visibility: "collapse"}}/>
        <Link id="video" to="/profile" style={{visibility: "collapse"}}/>
        <Link id="chat" to="/chat" style={{visibility: "collapse"}}/>
      </div>
    )
  }
}

export default Profile;
