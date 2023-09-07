const { pool } = require("../database");

function getKikobaMemberID(req, res) {
  var { userID, kikobaID } = req.body;

  //Query to retrieve an ID of member of a particular kikoba
  pool.query(
    'SELECT "memberID" FROM "member" WHERE "kikobaKey" = ' +
      kikobaID +
      ' AND "userKey" = '+
      userID+
      ";",
    (error, results) => {
      if (error) {
        throw error;
      }
      res.json(results.rows[0].memberID);
    }
  );
}

module.exports = { getKikobaMemberID };
