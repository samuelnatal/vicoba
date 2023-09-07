const { pool } = require("../database");

function getVikobaUserInvolvedIn(req, res) {

  var { userID } = req.body;

  pool.query(
    'SELECT "kikobaID", "kikobaName", region, district, ward FROM "address" JOIN "kikoba" ON "addressID" = "kikobaLocationID" JOIN "member" ON "kikobaID" = "kikobaKey" WHERE "userKey" = ' +
      userID +
      " AND status = 'Approved';",
    (error, results) => {
      if (error) {
        throw error;
      }
      res.json(results.rows);
    }
  );
}

module.exports = { getVikobaUserInvolvedIn };
