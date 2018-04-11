import React, { Component } from 'react'
import VideoCard from '../component/VideoCard.js'
import Header from '../component/Header.js'
import ArtistList from '../component/ArtistList.js'
import {getHomeData} from '../utils/fetchData.js'

class Home extends Component {
  constructor() {
    super()

    this.state = {
      videos: []
    }
  }

  componentDidMount() {
    getHomeData()
      .then(data => {
         if(data != null && data.VideoCards != null) {
           this.setState({videos: data.VideoCards}) 
         }
      })
      .catch(error => {
        window.location = "/login"
      })
  }

  getVideoCards = () => {
    const cards = [] 
    
    for(let i = 0; i < this.state.videos.length; i++) {
      const data = this.state.videos[i]
      cards.push(
        <VideoCard
          style={{margin: 40}}
          artist={data.Artist.Id}
          id={data.Id}
          desc={data.Desc}
          genre={data.Genre}
          name={data.Title}
          likes={data.Likes}
          views={data.Views}
        />
      )
    }

    return cards
  }

  render() {
    return (
      <div>
        <Header/>
        <div style={{display: "flex", flexDirection: "row"}}>
          <ArtistList/>
          <div style={{width: "-webkit-fill-available", display: "flex", flexDirection: "column", alignItems: "center"}}>
            {this.getVideoCards()}
          </div>
        </div>
      </div>
    )
  }
}

export default Home;
