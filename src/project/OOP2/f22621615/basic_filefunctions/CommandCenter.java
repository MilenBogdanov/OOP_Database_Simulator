package project.OOP2.f22621615.basic_filefunctions;

import project.OOP2.f22621615.database.Database;
import project.OOP2.f22621615.enums.CommandEnum;
import project.OOP2.f22621615.functionality.*;
import project.OOP2.f22621615.interfaces.Command;

import java.util.HashMap;
import java.util.Map;

public class CommandCenter {
    private OpenFileCommand openFileCommand;
    private Map<CommandEnum, Command> commands;
    private Database database;
    private StringBuilder fileContent;
    private String fileName;
    private String lastLoadedFile;

    public CommandCenter(Database database) {
        this.database = database;
        this.fileContent = new StringBuilder();
        this.fileName = "";
        this.openFileCommand = new OpenFileCommand(fileContent);
        initializeCommands();
    }

    private void initializeCommands() {
        commands = new HashMap<>();

        commands.put(CommandEnum.OPEN, new OpenFileCommand(fileContent));
        commands.put(CommandEnum.LOAD, new LoadTableFromTextFileCommand(database));
        commands.put(CommandEnum.CLOSE, new CloseFileCommand(fileContent));
        commands.put(CommandEnum.SAVE, new SaveFileCommand(fileName, fileContent, database));
        commands.put(CommandEnum.SAVEAS, new SaveFileAsCommand(fileContent));
        commands.put(CommandEnum.HELP, new PrintHelpCommand());
        commands.put(CommandEnum.EXIT, new ExitCommand());
        commands.put(CommandEnum.SHOWTABLES, new ShowTablesCommand(database));
        commands.put(CommandEnum.DESCRIBE, new DescribeTableCommand(database));
        commands.put(CommandEnum.PRINT, new PrintTableRowsCommand(database));
        commands.put(CommandEnum.EXPORT, new ExportTableCommand(database));
        commands.put(CommandEnum.SELECT, new SelectCommand(database));
        commands.put(CommandEnum.ADDCOLUMN, new AddColumnCommand(database));
        commands.put(CommandEnum.UPDATE, new UpdateCommand(database));
        commands.put(CommandEnum.DELETE, new DeleteCommand(database));
        commands.put(CommandEnum.INSERT, new InsertCommand(database));
        commands.put(CommandEnum.RENAME, new RenameTableCommand(database));
        commands.put(CommandEnum.COUNT, new CountRowsCommand(database));
        commands.put(CommandEnum.AGGREGATE, new AggregateCommand(database));
    }

    public void executeCommand(CommandEnum commandName, String parameter) {
        Command command = commands.get(commandName);
        if (command != null) {
            if (command instanceof UpdateCommand) {
                ((UpdateCommand) command).setFileName(lastLoadedFile);
            } else if (command instanceof SaveFileCommand) {
                ((SaveFileCommand) command).setFileName(lastLoadedFile);
            }
            command.execute(parameter);
            if (commandName == CommandEnum.LOAD) {
                lastLoadedFile = parameter;
            }
        } else {
            System.out.println("Invalid command. Type 'help' to see the list of commands.");
        }
    }
}