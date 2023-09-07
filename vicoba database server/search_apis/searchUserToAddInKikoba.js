const { pool } = require("../database");

function searchUserToAddInKikoba(req, res) {
  var { kikobaID, searchItem } = req.body;

  //Query to search lite items from user and kikoba tables inquired by a vicoba app user
  pool.query(
    'SELECT DISTINCT "userID", "firstName", "lastName" FROM "user" WHERE "userID" NOT IN (SELECT "userKey" FROM "member" WHERE "kikobaKey" = ' +
      kikobaID +
      ') AND "firstName" ILIKE \'%' +
      searchItem +
      "%' OR \"lastName\" ILIKE '%" +
      searchItem +
      "%' ;",
    (error, results) => {
      if (error) {
        throw error;
      }
      res.json(results.rows);
    }
  );
}
module.exports = { searchUserToAddInKikoba };
