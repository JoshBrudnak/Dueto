import React, { Component } from 'react'
import {Avatar, Typography, Paper, Tabs, Tab} from '@material-ui/core'
import Header from '../component/Header.js'
import VideoCard from '../component/VideoCard.js'
import MessageView from '../component/MessageView.js'
import {getArtistData, getSharedVideos} from '../utils/fetchData.js'

class Artist extends Component {
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
      avatarUrl: "/api/avatar?artist=0" 
    }
  }

  componentDidMount() {
    const id = this.props.match.params.name

    getArtistData(id)
      .then(data => {
        const avUrl = "/api/avatar?artist=" + id
        
        this.setState({
          id: id,
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
        console.error(error)
      })

    getSharedVideos()
      .then(data => {
        this.setState({reshares: data.VideoCards})
      })
      .catch(error => {
        console.error(error)
      })
  }

  tabChange = (event, value) => {
    switch(value) {
      case 0:
        this.setState({videoPage: true, chatPage: false, resharePage: false})
        break
      case 1:
        this.setState({videoPage: false, chatPage: true, resharePage: false})
        break
      case 2:
        this.setState({videoPage: false, chatPage: false, resharePage: true})
        break
      default:
        this.setState({videoPage: true, chatPage: false, resharePage: false})
        break
    }
  }

  edit = () => {
    document.getElementById("edit").click()
  }

  addVideo = () => {
    document.getElementById("addvideo").click()
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
      else {
        videoCards.push(
          <Paper style={{height: "fit-content", padding: 20, margin: 20}}>
            <Typography style={{fontSize: "large"}}>This user has no videos</Typography>
          </Paper>
        )
      }
    }
    else if(this.state.chatPage) {
      return (
        <MessageView style={{height: "fit-content", maxHeight: 800}} artist={this.state.id}/>
      )
    }
    else if(this.state.resharePage) {
      console.log("reshare")
      console.log(this.state.reshares)
      if(this.state.reshares !== null) {
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
      else {
        videoCards.push(
          <Paper style={{height: "fit-content", padding: 20, margin: 20}}>
            <Typography style={{fontSize: "large"}}>This user has no shared videos</Typography>
          </Paper>
        )
      }
    }

    return videoCards
  }

  render() {
    return (
      <div>
        <Header/>
        <div style={{backgroundColor: "#e8e8e8", display: "flex", flexDirection: "column", justifyContent: "flex-start"}}>
          <Paper style={{padding: 10, width: "-webkit-fill-available", display: "flex", flexDirection: "column", alignItems: "center"}}>
            <Avatar src={this.state.avatarUrl} style={{width: 100, height: 100, marginBotton: 10}}/>
            <Typography style={{fontSize: "large"}}>{this.state.name}</Typography> 
            <Typography style={{fontSize: "large"}}>{this.state.username}</Typography> 
          </Paper>
          <Paper style={{display: "grid", alignItems: "center"}}>
            <div style={{gridColumn: 2}}>
              <Tabs onChange={this.tabChange}>
                <Tab style={{width: 100}} label="Video" />
                <Tab style={{width: 100}} label="Chat" />
                <Tab style={{width: 100}} label="Reshared" />
              </Tabs>
            </div>
          </Paper>
        </div>
        <div style={{backgroundColor: "#e8e8e8", height: "-webkit-fill-available", display: "flex", flexWrap: "wrap", justifyContent: "center"}}>
          {this.getBody()}
        </div>
      </div>
    )
  }
}

export default Artist;
