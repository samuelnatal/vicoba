const { pool } = require("../database");

function generalSearch(req, res) {
  var { searchItem } = req.body;

  //Query to search lite items from user and kikoba tables inquired by a vicoba app user
  pool.query(
    'SELECT  COALESCE("firstName", \'Not found\') AS "firstName", COALESCE("lastName", \'Not found\') AS "lastName", COALESCE("kikobaName", \'Not found\') AS "kikobaName" FROM "user" FULL OUTER JOIN "kikoba" ON "userID" = "kikobaOwnerID" WHERE "firstName" ILIKE \'%' +
      searchItem +
      "%' OR \"lastName\" ILIKE '%" +
      searchItem +
      "%'OR \"kikobaName\" ILIKE '%" +
      searchItem +
      "%';",
    (error, results) => {
      if (error) {
        throw error;
      }
      res.json(results.rows);
    }
  );
}
module.exports = { generalSearch };
