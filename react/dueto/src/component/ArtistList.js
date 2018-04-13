import React, { Component } from 'react'
import { Divider, Typography, Paper, Button, Avatar } from 'material-ui'
import Card, { CardContent } from 'material-ui/Card'
import Menu, {MenuItem} from 'material-ui/Menu'
import {Link} from "react-router-dom"
import { getAvatarUrl, getArtistsByZip, getArtistsByCity } from '../utils/fetchData.js'

class ArtistList extends Component {
  constructor() {
    super()
  
    this.state = {
      open: false,
      anchor: undefined,
      buttonText: "By zip-code",
      artists: []
    }
  }

  componentDidMount() {
    getArtistsByZip()
      .then(data => {
        this.setState({artists: data})
      })
      .catch(error => {
        console.error(error)
      })
  }

  openMenu = event => {
    this.setState({ open: true, anchor: event.currentTarget});
  }

  closeMenu = event => {
    this.setState({ open: false });
  }
   
  zipcodeArtists = () => {
    getArtistsByZip()
      .then(data => {
        this.setState({artists: data, open: false, buttonText: "By zip-code"})
      })
      .catch(error => {
        console.error(error)
      })
  }

  cityArtists = () => {
    getArtistsByCity()
      .then(data => {
        this.setState({artists: data, open: false, buttonText: "By city"})
      })
      .catch(error => {
        console.error(error)
      })
  }

  getArtistCards = () => {
    const artistCards = []
    if(this.state.artists != null) { 
      for(let i = 0; i < this.state.artists.length; i++) {
        const data = this.state.artists[i]
        const profileUrl = "/artist/" + data.Id

        artistCards.push(
          <Link to={profileUrl} style={{textDecoration: "none"}}> 
            <Card style={{width: 200, margin: 20, display: "flex", paddingLeft: 10, alignItems: "center"}}>
              <Avatar alt={data.Name} src={getAvatarUrl(data.Id)}/>
              <CardContent>
                <Typography variant="headline" component="h2">
                  {data.Name}
                </Typography>
                <Typography>
                  {data.Username}
                </Typography>
              </CardContent>
            </Card>
          </Link>
        )
      }
    }
    else {
      artistCards.push(
        <Card style={{minWidth: 200, margin: 20, display: "flex", paddingLeft: 10, alignItems: "center"}}>
          <CardContent>
            <Typography>There are no artists in your area.</Typography>
          </CardContent>
        </Card>
      )
    }
 
    return artistCards
  }

  render() {
    return (
      <Paper style={{marginTop: 20}}>
        <div style={{margin: 10, minWidth: 250, display: "flex", flexDirection: "row"}}>
          <Typography style={{minWidth: 130, margin: 10, fontSize: "large"}}>Artists near you</Typography>
          <Button id="menu" style={{minWidth: 125}} onClick={this.openMenu}>{this.state.buttonText}</Button>
        </div>
        <Divider/>
        <Menu anchorEl={this.state.anchor} open={this.state.open} onClose={this.closeMenu}>
          <MenuItem onClick={this.zipcodeArtists}>By Zipcode</MenuItem>
          <MenuItem onClick={this.cityArtists}>By City</MenuItem>
        </Menu>
        {this.getArtistCards()}
      </Paper>
    )
  }
}

export default ArtistList
