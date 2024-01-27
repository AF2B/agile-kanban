package br.com.teamdevs.agilekanban.services.validations;

import org.springframework.stereotype.Service;

import br.com.teamdevs.agilekanban.model.Attachment;
import br.com.teamdevs.agilekanban.model.Comment;
import br.com.teamdevs.agilekanban.model.Member;
import br.com.teamdevs.agilekanban.model.Project;
import br.com.teamdevs.agilekanban.model.Task;
import br.com.teamdevs.agilekanban.model.User;
import br.com.teamdevs.agilekanban.services.validations.attachmentvalidations.AttachmentValidationPropertiesService;
import br.com.teamdevs.agilekanban.services.validations.commentvalidations.CommentValidationPropertiesService;
import br.com.teamdevs.agilekanban.services.validations.membervalidations.MemberValidationPropertiesService;
import br.com.teamdevs.agilekanban.services.validations.projectvalidations.ProjectValidationPropertiesService;
import br.com.teamdevs.agilekanban.services.validations.taskvalidation.TaskValidationPropertiesService;
import br.com.teamdevs.agilekanban.services.validations.uservalidations.UserValidationPropertiesService;

@Service
public class ValidationFacadeService {
    public ValidationFacadeService(
            AttachmentValidationPropertiesService attachmentValidationService,
            CommentValidationPropertiesService commentValidationService,
            MemberValidationPropertiesService memberValidationService,
            ProjectValidationPropertiesService projectValidationService,
            TaskValidationPropertiesService taskValidationService) {
    }

    public void validateAll(
            Attachment attachment,
            Comment comment,
            Member member,
            Project project,
            Task task,
            User user) {
        AttachmentValidationPropertiesService.validate(attachment.getFilename());
        CommentValidationPropertiesService.validate(comment.getText());
        MemberValidationPropertiesService.validate(member.getRole());
        ProjectValidationPropertiesService.validate(project.getName());
        TaskValidationPropertiesService.validate(task.getTitle());
        UserValidationPropertiesService.validate(user.getEmail(), user.getPassword(), user.getUsername());
    }
}
