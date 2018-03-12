import React, { Component } from 'react'
import VideoCard from '../component/VideoCard.js'
import Header from '../component/Header.js'
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
         this.setState({videos: data.VideoCards}) 
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
          artist={data.Artist.Id}
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
        {this.getVideoCards()}
      </div>
    )
  }
}

export default Home;
