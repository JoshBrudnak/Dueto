import React, { Component } from 'react';
import Header from '../component/Header.js'
import GenreCard from '../component/GenreCard.js'
import {getGenres} from '../utils/fetchData.js'

class Discover extends Component {
  constructor() {
    super()
    
    this.state = {
      genres: [],
      selGenre: undefined
    }
  }

  componentDidMount() {
    getGenres()
      .then(data => {
        console.log(data.GenreList)
        this.setState({genres: data.GenreList})
      })
      .catch(error => {
        console.error(error)
      })
  }

  getCardRow = (index, cardsLen) => {
    const rCards = []

    for(let i = index; i < (index + 2) && i < this.state.genres.length; i++) {
      let genre = this.state.genres[i]

      rCards.push(
        <div style={{margin: 40}}>
          <GenreCard name={genre.Name} desc={genre.Description}/>
        </div>
      )
    }
   
    return rCards
  }

  getGenres = () => {
    const gCards = []

    for(let i = 0; i < this.state.genres.length; i += 2) {
        gCards.push(
          <div style={{display: "flex", flexDirection: "row"}}>
            {this.getCardRow(i)}
          </div>
        )
    }
    
    return gCards
  }

  render() {
    return (
      <div>
        <Header/>
        <div style={{margin: 20, display: "flex", flexDirection: "column", alignItems: "center"}}>
          {this.getGenres()}
        </div>
      </div>
    );
  }
}

export default Discover;
