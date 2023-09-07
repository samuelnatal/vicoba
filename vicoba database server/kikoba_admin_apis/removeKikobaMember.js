const { pool } = require("../database");

function removeKikobaMember(req, res) {
  var { userID, kikobaID } = req.body;

  //Query to retrieve kikoba information user have been invited for
  pool.query(
    'DELETE FROM "member" WHERE "userKey" = ' +
      userID +
      ' AND "kikobaKey" = ' +
      kikobaID +
      ";",
    (error, results) => {
      if (error) {
        throw error;
      }
      res.json(true);
    }
  );
}

module.exports = { removeKikobaMember };
