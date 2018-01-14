//
//  TableViewCell1.swift
//  Dueto
//
//  Created by Lily Suau on 1/12/18.
//  Copyright © 2018 Dueto. All rights reserved.
//

import UIKit

class TableViewCell1: UITableViewCell {
    
    @IBOutlet weak var mainImageView: UIImageView!
    @IBOutlet weak var mainLabel: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
}
