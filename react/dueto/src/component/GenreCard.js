import React, { Component } from 'react'
import { Card, Button, Typography } from 'material-ui'
import { CardMedia} from 'material-ui/Card'
import {Link} from 'react-router-dom'

class GenreCard extends Component {
  render() {
    let genreUrl = "/genre?name=" + this.props.name
    let genreImUrl = "/api/genreimage?name=" + this.props.name
    console.log(genreUrl)
    console.log(genreImUrl)

    return (
        <Card>
          <Link to={genreUrl}>
            <CardMedia>
              <img src={genreImUrl} style={{width: 400, height: 400}}/>
            </CardMedia>
          </Link>
        </Card>
    )
  }
}

export default GenreCard
