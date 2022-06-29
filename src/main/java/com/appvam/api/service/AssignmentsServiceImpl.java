package com.appvam.api.service;

import com.appvam.api.models.Assignment;
import com.appvam.api.repository.AssignmentsRepository;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Slf4j
@RequiredArgsConstructor
@Service
public class AssignmentsServiceImpl implements AssignmentsService {

    private AssignmentsRepository assignmentsRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Configuration config;


    @Override
    public Assignment get(Long id) {
        return assignmentsRepository.getById(id);
    }

    @Override
    public List<Assignment> getAll(String receiver) {
        return receiver != null
                ? assignmentsRepository.findAssignmentsByReceiver(receiver)
                : assignmentsRepository.findAll();
    }

    @Override
    public void send(Assignment assignment) {
        MimeMessage message = javaMailSender.createMimeMessage();
        Map<String, Object> model = new HashMap<>();
        try {
            model.put("deadline", assignment.getDeadline().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
            model.put("link", "");

            MimeMessageHelper helper = new MimeMessageHelper(
                    message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name()
            );

            Template t = config.getTemplate("assigment-template.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

            helper.setTo(assignment.getReceiver());
            helper.setText(html, true);
            helper.setSubject("Appvam - New assigment");
            helper.setFrom("Appvam <no-reply>@appvam.com");
            javaMailSender.send(message);

            log.info("mail send to : " + assignment.getReceiver());

            assignment.setToken(generateToken());
            assignmentsRepository.save(assignment);

        } catch (Exception e) {
            log.error("An error occurred while sending mail -> ", e);
        }
    }

    private String generateToken() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 42;
        Random random = new Random();

        return random
                .ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    @Override
    public void delete(Long id) {
        assignmentsRepository.deleteById(id);
    }

    @Autowired
    public void setAssignmentsRepository(AssignmentsRepository assignmentsRepository) {
        this.assignmentsRepository = assignmentsRepository;
    }
}
