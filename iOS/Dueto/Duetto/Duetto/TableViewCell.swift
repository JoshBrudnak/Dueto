//
//  TableViewCell.swift
//  MVC
//
//  Created by Mark Moeykens on 5/2/17.
//  Copyright © 2017 Moeykens. All rights reserved.
//

import UIKit

class TableViewCell: UITableViewCell {

    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var subtitleLabel: UILabel!
    @IBOutlet weak var cellImageView: UIImageView!

    func setup(model: Model) {
        titleLabel.text = model.title
        subtitleLabel.text = model.subTitle
        cellImageView.image = model.image
        
    }

}
