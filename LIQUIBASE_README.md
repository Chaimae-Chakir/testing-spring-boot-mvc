# Liquibase Database Migration Setup

This project uses Liquibase for database schema and data management. Liquibase provides version control for database changes and ensures consistent database state across different environments.

## Configuration

### Application Properties
The Liquibase configuration is set in `src/main/resources/application.yml`:

```yaml
spring:
  liquibase:
    enabled: true
    change-log: classpath:db/changelog/db.changelog-master.yaml
```

### Changelog Structure
- `db.changelog-master.yaml` - Master changelog that includes all other changelogs
- `001-initial-schema.yaml` - Creates the initial database schema
- `002-initial-data.yaml` - Inserts initial sample data

## Database Schema

### Product Table
```sql
CREATE TABLE product (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL UNIQUE,
    price DOUBLE NOT NULL
);
```

## Sample Data
The following products are automatically inserted:
- Phone: $999.99
- Laptop: $1499.99
- Tablet: $599.99

## Running Migrations

### Automatic Migration
Liquibase automatically runs migrations when the application starts. The database schema and data will be created automatically.

### Manual Migration
To run migrations manually, you can use the Liquibase CLI or Maven plugin:

```bash
# Using Maven plugin
mvn liquibase:update

# Using Liquibase CLI
liquibase --changeLogFile=src/main/resources/db/changelog/db.changelog-master.yaml update
```

## Adding New Changes

To add new database changes:

1. Create a new changelog file (e.g., `003-add-new-feature.yaml`)
2. Add your changes using Liquibase XML/YAML format
3. Include the new changelog in `db.changelog-master.yaml`

Example:
```yaml
databaseChangeLog:
  - include:
      file: db/changelog/003-add-new-feature.yaml
```

## Best Practices

1. **Always use preConditions** to make changes idempotent
2. **Use meaningful changeSet IDs** that include timestamps
3. **Add comments** to describe what each changeSet does
4. **Test migrations** in development before applying to production
5. **Keep changelogs in version control** for tracking database evolution

## Troubleshooting

### Common Issues
1. **Migration fails**: Check the changelog syntax and ensure all referenced files exist
2. **Data conflicts**: Use preConditions to handle existing data gracefully
3. **Schema conflicts**: Ensure table/column names match exactly

### H2 Console
Access the H2 database console at: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:testDb`
- Username: `sa`
- Password: (empty) 